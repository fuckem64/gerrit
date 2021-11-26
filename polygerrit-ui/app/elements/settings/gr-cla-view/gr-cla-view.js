/**
 * @license
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import '@polymer/iron-input/iron-input.js';
import '../../../scripts/bundled-polymer.js';
import '../../../styles/gr-form-styles.js';
import '../../../styles/shared-styles.js';
import '../../shared/gr-button/gr-button.js';
import '../../shared/gr-rest-api-interface/gr-rest-api-interface.js';
import {mixinBehaviors} from '@polymer/polymer/lib/legacy/class.js';
import {GestureEventListeners} from '@polymer/polymer/lib/mixins/gesture-event-listeners.js';
import {LegacyElementMixin} from '@polymer/polymer/lib/legacy/legacy-element-mixin.js';
import {PolymerElement} from '@polymer/polymer/polymer-element.js';
import {htmlTemplate} from './gr-cla-view_html.js';
import {BaseUrlBehavior} from '../../../behaviors/base-url-behavior/base-url-behavior.js';

/**
 * @extends Polymer.Element
 */
class GrClaView extends mixinBehaviors( [
  BaseUrlBehavior,
], GestureEventListeners(
    LegacyElementMixin(
        PolymerElement))) {
  static get template() { return htmlTemplate; }

  static get is() { return 'gr-cla-view'; }

  static get properties() {
    return {
      _groups: Object,
      /** @type {?} */
      _serverConfig: Object,
      _agreementsText: String,
      _agreementName: String,
      _signedAgreements: Array,
      _showAgreements: {
        type: Boolean,
        value: false,
      },
      _agreementsUrl: String,
    };
  }

  /** @override */
  attached() {
    super.attached();
    this.loadData();

    this.dispatchEvent(new CustomEvent('title-change', {
      detail: {title: 'New Contributor Agreement'},
      composed: true, bubbles: true,
    }));
  }

  loadData() {
    const promises = [];
    promises.push(this.$.restAPI.getConfig(true).then(config => {
      this._serverConfig = config;
    }));

    promises.push(this.$.restAPI.getAccountGroups().then(groups => {
      this._groups = groups.sort((a, b) => a.name.localeCompare(b.name));
    }));

    promises.push(this.$.restAPI.getAccountAgreements().then(agreements => {
      this._signedAgreements = agreements || [];
    }));

    return Promise.all(promises);
  }

  _getAgreementsUrl(configUrl) {
    let url;
    if (!configUrl) {
      return '';
    }
    if (configUrl.startsWith('http:') || configUrl.startsWith('https:')) {
      url = configUrl;
    } else {
      url = this.getBaseUrl() + '/' + configUrl;
    }

    return url;
  }

  _handleShowAgreement(e) {
    this._agreementName = e.target.getAttribute('data-name');
    this._agreementsUrl =
        this._getAgreementsUrl(e.target.getAttribute('data-url'));
    this._showAgreements = true;
  }

  _handleSaveAgreements(e) {
    this._createToast('Agreement saving...');

    const name = this._agreementName;
    return this.$.restAPI.saveAccountAgreement({name}).then(res => {
      let message = 'Agreement failed to be submitted, please try again';
      if (res.status === 200) {
        message = 'Agreement has been successfully submited.';
      }
      this._createToast(message);
      this.loadData();
      this._agreementsText = '';
      this._showAgreements = false;
    });
  }

  _createToast(message) {
    this.dispatchEvent(new CustomEvent(
        'show-alert', {detail: {message}, bubbles: true, composed: true}));
  }

  _computeShowAgreementsClass(agreements) {
    return agreements ? 'show' : '';
  }

  _disableAgreements(item, groups, signedAgreements) {
    if (!groups) return false;
    for (const group of groups) {
      if ((item && item.auto_verify_group &&
          item.auto_verify_group.id === group.id) ||
          signedAgreements.find(i => i.name === item.name)) {
        return true;
      }
    }
    return false;
  }

  _hideAgreements(item, groups, signedAgreements) {
    return this._disableAgreements(item, groups, signedAgreements) ?
      '' : 'hide';
  }

  _disableAgreementsText(text) {
    return text.toLowerCase() === 'i agree' ? false : true;
  }

  // This checks for auto_verify_group,
  // if specified it returns 'hideAgreementsTextBox' which
  // then hides the text box and submit button.
  _computeHideAgreementClass(name, config) {
    if (!config) return '';
    for (const key in config) {
      if (!config.hasOwnProperty(key)) {
        continue;
      }
      for (const prop in config[key]) {
        if (!config[key].hasOwnProperty(prop)) {
          continue;
        }
        if (name === config[key].name &&
            !config[key].auto_verify_group) {
          return 'hideAgreementsTextBox';
        }
      }
    }
  }
}

customElements.define(GrClaView.is, GrClaView);