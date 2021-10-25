/**
 * @license
 * Copyright (C) 2020 The Android Open Source Project
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

/**
 * @desc Tab names for primary tabs on change view page.
 */
import {DiffViewMode} from '../api/diff';
import {DiffPreferencesInfo} from '../types/diff';
import {EditPreferencesInfo, PreferencesInfo} from '../types/common';
import {
  AuthType,
  ChangeStatus,
  ConfigParameterInfoType,
  DefaultDisplayNameConfig,
  EditableAccountField,
  FileInfoStatus,
  GpgKeyInfoStatus,
  HttpMethod,
  InheritedBooleanInfoConfiguredValue,
  MergeabilityComputationBehavior,
  ProblemInfoStatus,
  ProjectState,
  RequirementStatus,
  ReviewerState,
  RevisionKind,
  SubmitType,
} from '../api/rest-api';

export {
  AuthType,
  ChangeStatus,
  ConfigParameterInfoType,
  DefaultDisplayNameConfig,
  EditableAccountField,
  FileInfoStatus,
  GpgKeyInfoStatus,
  HttpMethod,
  InheritedBooleanInfoConfiguredValue,
  MergeabilityComputationBehavior,
  ProblemInfoStatus,
  ProjectState,
  RequirementStatus,
  ReviewerState,
  RevisionKind,
  SubmitType,
};

export enum AccountTag {
  SERVICE_USER = 'SERVICE_USER',
}

export enum PrimaryTab {
  FILES = 'files',
  /**
   * When renaming 'comments' or 'findings', UrlFormatter.java must be updated.
   */
  COMMENT_THREADS = 'comments',
  FINDINGS = 'findings',
  CHECKS = 'checks',
}

/**
 * @desc Tab names for secondary tabs on change view page.
 */
export enum SecondaryTab {
  CHANGE_LOG = '_changeLog',
}

/**
 * @desc Tag names of change log messages.
 */
export enum MessageTag {
  TAG_DELETE_REVIEWER = 'autogenerated:gerrit:deleteReviewer',
  TAG_NEW_PATCHSET = 'autogenerated:gerrit:newPatchSet',
  TAG_NEW_WIP_PATCHSET = 'autogenerated:gerrit:newWipPatchSet',
  TAG_REVIEWER_UPDATE = 'autogenerated:gerrit:reviewerUpdate',
  TAG_SET_PRIVATE = 'autogenerated:gerrit:setPrivate',
  TAG_UNSET_PRIVATE = 'autogenerated:gerrit:unsetPrivate',
  TAG_SET_READY = 'autogenerated:gerrit:setReadyForReview',
  TAG_SET_WIP = 'autogenerated:gerrit:setWorkInProgress',
  TAG_SET_ASSIGNEE = 'autogenerated:gerrit:setAssignee',
  TAG_UNSET_ASSIGNEE = 'autogenerated:gerrit:deleteAssignee',
  TAG_MERGED = 'autogenerated:gerrit:merged',
  TAG_REVERT = 'autogenerated:gerrit:revert',
}

/**
 * @desc Modes for gr-diff-cursor
 * The scroll behavior for the cursor. Values are 'never' and
 * 'keep-visible'. 'keep-visible' will only scroll if the cursor is beyond
 * the viewport.
 */
export enum ScrollMode {
  KEEP_VISIBLE = 'keep-visible',
  NEVER = 'never',
}

/**
 * @desc Special file paths
 */
export enum SpecialFilePath {
  PATCHSET_LEVEL_COMMENTS = '/PATCHSET_LEVEL',
  COMMIT_MESSAGE = '/COMMIT_MSG',
  MERGE_LIST = '/MERGE_LIST',
}

export {Side} from '../api/diff';

/**
 * https://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#mergeable-info
 */
export enum MergeStrategy {
  RECURSIVE = 'recursive',
  RESOLVE = 'resolve',
  SIMPLE_TWO_WAY_IN_CORE = 'simple-two-way-in-core',
  OURS = 'ours',
  THEIRS = 'theirs',
}

/**
 * Enum for possible PermissionRuleInfo actions
 * https://gerrit-review.googlesource.com/Documentation/rest-api-access.html#permission-info
 */
export enum PermissionAction {
  ALLOW = 'ALLOW',
  DENY = 'DENY',
  BLOCK = 'BLOCK',
  // Special values for global capabilities
  INTERACTIVE = 'INTERACTIVE',
  BATCH = 'BATCH',
}

/**
 * This capability allows users to use the thread pool reserved for 'Non-Interactive Users'.
 * https://gerrit-review.googlesource.com/Documentation/access-control.html#capability_priority
 */
export enum UserPriority {
  BATCH = 'BATCH',
  INTERACTIVE = 'INTERACTIVE',
}

/**
 * The side on which the comment was added
 * https://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#comment-info
 */
export enum CommentSide {
  REVISION = 'REVISION',
  PARENT = 'PARENT',
}

/**
 * Allowed app themes
 * https://gerrit-review.googlesource.com/Documentation/rest-api-accounts.html#preferences-input
 */
export enum AppTheme {
  DARK = 'DARK',
  LIGHT = 'LIGHT',
}

/**
 * Date formats in preferences
 * https://gerrit-review.googlesource.com/Documentation/rest-api-accounts.html#preferences-input
 */
export enum DateFormat {
  STD = 'STD',
  US = 'US',
  ISO = 'ISO',
  EURO = 'EURO',
  UK = 'UK',
}

/**
 * Time formats in preferences
 * https://gerrit-review.googlesource.com/Documentation/rest-api-accounts.html#preferences-input
 */
export enum TimeFormat {
  HHMM_12 = 'HHMM_12',
  HHMM_24 = 'HHMM_24',
}

export {DiffViewMode};

/**
 * The type of email strategy to use.
 * https://gerrit-review.googlesource.com/Documentation/rest-api-accounts.html#preferences-input
 */
export enum EmailStrategy {
  ENABLED = 'ENABLED',
  CC_ON_OWN_COMMENTS = 'CC_ON_OWN_COMMENTS',
  ATTENTION_SET_ONLY = 'ATTENTION_SET_ONLY',
  DISABLED = 'DISABLED',
}

/**
 * The type of email format to use.
 * Doesn't mentioned in doc, but exists in Java class GeneralPreferencesInfo.
 */

export enum EmailFormat {
  PLAINTEXT = 'PLAINTEXT',
  HTML_PLAINTEXT = 'HTML_PLAINTEXT',
}

/**
 * The base which should be pre-selected in the 'Diff Against' drop-down list when the change screen is opened for a merge commit
 * https://gerrit-review.googlesource.com/Documentation/rest-api-accounts.html#preferences-input
 */
export enum DefaultBase {
  AUTO_MERGE = 'AUTO_MERGE',
  FIRST_PARENT = 'FIRST_PARENT',
}

/**
 * how draft comments are handled
 */
export enum DraftsAction {
  PUBLISH = 'PUBLISH',
  PUBLISH_ALL_REVISIONS = 'PUBLISH_ALL_REVISIONS',
  KEEP = 'KEEP',
}

export enum NotifyType {
  NONE = 'NONE',
  OWNER = 'OWNER',
  OWNER_REVIEWERS = 'OWNER_REVIEWERS',
  ALL = 'ALL',
}

/**
 * Controls visibility of other users' dashboard pages and completion suggestions to web users
 * https://gerrit-review.googlesource.com/Documentation/config-gerrit.html#accounts.visibility
 */
export enum AccountsVisibility {
  ALL = 'ALL',
  SAME_GROUP = 'SAME_GROUP',
  VISIBLE_GROUP = 'VISIBLE_GROUP',
  NONE = 'NONE',
}

// TODO(TS): Many properties are omitted here, but they are required.
// Add default values for missing properties.
export function createDefaultPreferences() {
  return {
    changes_per_page: 25,
    diff_view: DiffViewMode.SIDE_BY_SIDE,
    size_bar_in_change_table: true,
  } as PreferencesInfo;
}

// These defaults should match the defaults in
// java/com/google/gerrit/extensions/client/DiffPreferencesInfo.java
// NOTE: There are some settings that don't apply to PolyGerrit
// (Render mode being at least one of them).
export function createDefaultDiffPrefs(): DiffPreferencesInfo {
  return {
    context: 10,
    cursor_blink_rate: 0,
    font_size: 12,
    ignore_whitespace: 'IGNORE_NONE',
    line_length: 100,
    line_wrapping: false,
    show_line_endings: true,
    show_tabs: true,
    show_whitespace_errors: true,
    syntax_highlighting: true,
    tab_size: 8,
  };
}

// These defaults should match the defaults in
// java/com/google/gerrit/extensions/client/EditPreferencesInfo.java
export function createDefaultEditPrefs(): EditPreferencesInfo {
  return {
    auto_close_brackets: false,
    cursor_blink_rate: 0,
    hide_line_numbers: false,
    hide_top_menu: false,
    indent_unit: 2,
    indent_with_tabs: false,
    key_map_type: 'DEFAULT',
    line_length: 100,
    line_wrapping: false,
    match_brackets: true,
    show_base: false,
    show_tabs: true,
    show_whitespace_errors: true,
    syntax_highlighting: true,
    tab_size: 8,
    theme: 'DEFAULT',
  };
}

export const RELOAD_DASHBOARD_INTERVAL_MS = 10 * 1000;

export const SHOWN_ITEMS_COUNT = 25;
