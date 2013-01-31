// Copyright (C) 2008 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.client.admin;

import com.google.gerrit.client.Gerrit;
import com.google.gerrit.client.groups.GroupApi;
import com.google.gerrit.client.rpc.GerritCallback;
import com.google.gerrit.client.ui.AccountGroupSuggestOracle;
import com.google.gerrit.client.ui.OnEditEnabler;
import com.google.gerrit.client.ui.RPCSuggestOracle;
import com.google.gerrit.client.ui.SmallHeading;
import com.google.gerrit.common.data.GroupDetail;
import com.google.gerrit.common.data.GroupOptions;
import com.google.gerrit.reviewdb.client.AccountGroup;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwtexpui.clippy.client.CopyableLabel;
import com.google.gwtexpui.globalkey.client.NpTextArea;
import com.google.gwtexpui.globalkey.client.NpTextBox;
import com.google.gwtjsonrpc.common.VoidResult;

public class AccountGroupInfoScreen extends AccountGroupScreen {
  private CopyableLabel groupUUIDLabel;

  private NpTextBox groupNameTxt;
  private Button saveName;

  private NpTextBox ownerTxtBox;
  private SuggestBox ownerTxt;
  private Button saveOwner;

  private NpTextArea descTxt;
  private Button saveDesc;

  private CheckBox visibleToAllCheckBox;
  private Button saveGroupOptions;

  public AccountGroupInfoScreen(final GroupDetail toShow, final String token) {
    super(toShow, token);
  }

  @Override
  protected void onInitUI() {
    super.onInitUI();
    initUUID();
    initName();
    initOwner();
    initDescription();
    initGroupOptions();
  }

  private void enableForm(final boolean canModify) {
    groupNameTxt.setEnabled(canModify);
    ownerTxtBox.setEnabled(canModify);
    descTxt.setEnabled(canModify);
    visibleToAllCheckBox.setEnabled(canModify);
  }

  private void initUUID() {
    final VerticalPanel groupUUIDPanel = new VerticalPanel();
    groupUUIDPanel.setStyleName(Gerrit.RESOURCES.css().groupUUIDPanel());
    groupUUIDPanel.add(new SmallHeading(Util.C.headingGroupUUID()));
    groupUUIDLabel = new CopyableLabel("");
    groupUUIDPanel.add(groupUUIDLabel);
    add(groupUUIDPanel);
  }

  private void initName() {
    final VerticalPanel groupNamePanel = new VerticalPanel();
    groupNamePanel.setStyleName(Gerrit.RESOURCES.css().groupNamePanel());
    groupNameTxt = new NpTextBox();
    groupNameTxt.setStyleName(Gerrit.RESOURCES.css().groupNameTextBox());
    groupNameTxt.setVisibleLength(60);
    groupNamePanel.add(groupNameTxt);

    saveName = new Button(Util.C.buttonRenameGroup());
    saveName.setEnabled(false);
    saveName.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        final String newName = groupNameTxt.getText().trim();
        Util.GROUP_SVC.renameGroup(getGroupId(), newName,
            new GerritCallback<GroupDetail>() {
              public void onSuccess(final GroupDetail groupDetail) {
                saveName.setEnabled(false);
                setPageTitle(Util.M.group(groupDetail.group.getName()));
                display(groupDetail);
              }
            });
      }
    });
    groupNamePanel.add(saveName);
    add(groupNamePanel);

    new OnEditEnabler(saveName, groupNameTxt);
  }

  private void initOwner() {
    final VerticalPanel ownerPanel = new VerticalPanel();
    ownerPanel.setStyleName(Gerrit.RESOURCES.css().groupOwnerPanel());
    ownerPanel.add(new SmallHeading(Util.C.headingOwner()));

    ownerTxtBox = new NpTextBox();
    ownerTxtBox.setVisibleLength(60);
    ownerTxt = new SuggestBox(new RPCSuggestOracle(
        new AccountGroupSuggestOracle()), ownerTxtBox);
    ownerTxt.setStyleName(Gerrit.RESOURCES.css().groupOwnerTextBox());
    ownerPanel.add(ownerTxt);

    saveOwner = new Button(Util.C.buttonChangeGroupOwner());
    saveOwner.setEnabled(false);
    saveOwner.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        final String newOwner = ownerTxt.getText().trim();
        if (newOwner.length() > 0) {
          GroupApi.setGroupOwner(getGroupUUID(), newOwner,
              new GerritCallback<com.google.gerrit.client.VoidResult>() {
                public void onSuccess(final com.google.gerrit.client.VoidResult result) {
                  saveOwner.setEnabled(false);
                }
              });
        }
      }
    });
    ownerPanel.add(saveOwner);
    add(ownerPanel);

    new OnEditEnabler(saveOwner, ownerTxtBox);
  }

  private void initDescription() {
    final VerticalPanel vp = new VerticalPanel();
    vp.setStyleName(Gerrit.RESOURCES.css().groupDescriptionPanel());
    vp.add(new SmallHeading(Util.C.headingDescription()));

    descTxt = new NpTextArea();
    descTxt.setVisibleLines(6);
    descTxt.setCharacterWidth(60);
    vp.add(descTxt);

    saveDesc = new Button(Util.C.buttonSaveDescription());
    saveDesc.setEnabled(false);
    saveDesc.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        final String txt = descTxt.getText().trim();
        GroupApi.setGroupDescription(getGroupUUID(), txt,
            new GerritCallback<com.google.gerrit.client.VoidResult>() {
              public void onSuccess(final com.google.gerrit.client.VoidResult result) {
                saveDesc.setEnabled(false);
              }
            });
      }
    });
    vp.add(saveDesc);
    add(vp);

    new OnEditEnabler(saveDesc, descTxt);
  }

  private void initGroupOptions() {
    final VerticalPanel groupOptionsPanel = new VerticalPanel();

    final VerticalPanel vp = new VerticalPanel();
    vp.setStyleName(Gerrit.RESOURCES.css().groupOptionsPanel());
    vp.add(new SmallHeading(Util.C.headingGroupOptions()));

    visibleToAllCheckBox = new CheckBox(Util.C.isVisibleToAll());
    vp.add(visibleToAllCheckBox);
    groupOptionsPanel.add(vp);

    saveGroupOptions = new Button(Util.C.buttonSaveGroupOptions());
    saveGroupOptions.setEnabled(false);
    saveGroupOptions.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        final GroupOptions groupOptions =
            new GroupOptions(visibleToAllCheckBox.getValue());
        Util.GROUP_SVC.changeGroupOptions(getGroupId(), groupOptions,
            new GerritCallback<VoidResult>() {
              public void onSuccess(final VoidResult result) {
                saveGroupOptions.setEnabled(false);
              }
            });
      }
    });
    groupOptionsPanel.add(saveGroupOptions);

    add(groupOptionsPanel);

    final OnEditEnabler enabler = new OnEditEnabler(saveGroupOptions);
    enabler.listenTo(visibleToAllCheckBox);
  }

  @Override
  protected void display(final GroupDetail groupDetail) {
    final AccountGroup group = groupDetail.group;
    groupUUIDLabel.setText(group.getGroupUUID().get());
    groupNameTxt.setText(group.getName());
    if (groupDetail.ownerGroup != null) {
      ownerTxt.setText(groupDetail.ownerGroup.getName());
    } else {
      ownerTxt.setText(Util.M.deletedReference(group.getOwnerGroupUUID().get()));
    }
    descTxt.setText(group.getDescription());
    visibleToAllCheckBox.setValue(group.isVisibleToAll());
    setMembersTabVisible(group.getType() == AccountGroup.Type.INTERNAL);

    enableForm(groupDetail.canModify);
    saveName.setVisible(groupDetail.canModify);
    saveOwner.setVisible(groupDetail.canModify);
    saveDesc.setVisible(groupDetail.canModify);
    saveGroupOptions.setVisible(groupDetail.canModify);
  }
}
