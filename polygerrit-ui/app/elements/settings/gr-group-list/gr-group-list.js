/**
 * @license
 * Copyright (C) 2016 The Android Open Source Project
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
import '../../../scripts/bundled-polymer.js';

import '../../../styles/shared-styles.js';
import '../../../styles/gr-form-styles.js';
import '../../shared/gr-rest-api-interface/gr-rest-api-interface.js';
import {GestureEventListeners} from '@polymer/polymer/lib/mixins/gesture-event-listeners.js';
import {LegacyElementMixin} from '@polymer/polymer/lib/legacy/legacy-element-mixin.js';
import {PolymerElement} from '@polymer/polymer/polymer-element.js';
import {htmlTemplate} from './gr-group-list_html.js';
import {GerritNav} from '../../core/gr-navigation/gr-navigation.js';

/** @extends Polymer.Element */
class GrGroupList extends GestureEventListeners(
    LegacyElementMixin(
        PolymerElement)) {
  static get template() { return htmlTemplate; }

  static get is() { return 'gr-group-list'; }

  static get properties() {
    return {
      _groups: Array,
    };
  }

  loadData() {
    return this.$.restAPI.getAccountGroups().then(groups => {
      this._groups = groups.sort((a, b) => a.name.localeCompare(b.name));
    });
  }

  _computeVisibleToAll(group) {
    return group.options.visible_to_all ? 'Yes' : 'No';
  }

  _computeGroupPath(group) {
    if (!group || !group.id) { return; }

    // Group ID is already encoded from the API
    // Decode it here to match with our router encoding behavior
    return GerritNav.getUrlForGroup(decodeURIComponent(group.id));
  }
}

customElements.define(GrGroupList.is, GrGroupList);