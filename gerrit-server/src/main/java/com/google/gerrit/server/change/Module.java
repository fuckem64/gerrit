// Copyright (C) 2012 The Android Open Source Project
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

package com.google.gerrit.server.change;

import static com.google.gerrit.server.change.ChangeEditResource.CHANGE_EDIT_KIND;
import static com.google.gerrit.server.change.ChangeResource.CHANGE_KIND;
import static com.google.gerrit.server.change.CommentResource.COMMENT_KIND;
import static com.google.gerrit.server.change.DraftCommentResource.DRAFT_COMMENT_KIND;
import static com.google.gerrit.server.change.FileResource.FILE_KIND;
import static com.google.gerrit.server.change.FixResource.FIX_KIND;
import static com.google.gerrit.server.change.ReviewerResource.REVIEWER_KIND;
import static com.google.gerrit.server.change.RevisionResource.REVISION_KIND;
import static com.google.gerrit.server.change.RobotCommentResource.ROBOT_COMMENT_KIND;
import static com.google.gerrit.server.change.VoteResource.VOTE_KIND;

import com.google.gerrit.extensions.registration.DynamicMap;
import com.google.gerrit.extensions.restapi.RestApiModule;
import com.google.gerrit.server.account.AccountLoader;
import com.google.gerrit.server.change.Reviewed.DeleteReviewed;
import com.google.gerrit.server.change.Reviewed.PutReviewed;

public class Module extends RestApiModule {
  @Override
  protected void configure() {
    bind(ChangesCollection.class);
    bind(Revisions.class);
    bind(Reviewers.class);
    bind(RevisionReviewers.class);
    bind(DraftComments.class);
    bind(Comments.class);
    bind(RobotComments.class);
    bind(Fixes.class);
    bind(Files.class);
    bind(Votes.class);

    DynamicMap.mapOf(binder(), CHANGE_KIND);
    DynamicMap.mapOf(binder(), COMMENT_KIND);
    DynamicMap.mapOf(binder(), ROBOT_COMMENT_KIND);
    DynamicMap.mapOf(binder(), FIX_KIND);
    DynamicMap.mapOf(binder(), DRAFT_COMMENT_KIND);
    DynamicMap.mapOf(binder(), FILE_KIND);
    DynamicMap.mapOf(binder(), REVIEWER_KIND);
    DynamicMap.mapOf(binder(), REVISION_KIND);
    DynamicMap.mapOf(binder(), CHANGE_EDIT_KIND);
    DynamicMap.mapOf(binder(), VOTE_KIND);

    get(CHANGE_KIND).to(GetChange.class);
    post(CHANGE_KIND, "merge").to(CreateMergePatchSet.class);
    get(CHANGE_KIND, "detail").to(GetDetail.class);
    get(CHANGE_KIND, "topic").to(GetTopic.class);
    get(CHANGE_KIND, "in").to(ChangeIncludedIn.class);
    get(CHANGE_KIND, "assignee").to(GetAssignee.class);
    get(CHANGE_KIND, "past_assignees").to(GetPastAssignees.class);
    put(CHANGE_KIND, "assignee").to(PutAssignee.class);
    delete(CHANGE_KIND, "assignee").to(DeleteAssignee.class);
    get(CHANGE_KIND, "hashtags").to(GetHashtags.class);
    get(CHANGE_KIND, "comments").to(ListChangeComments.class);
    get(CHANGE_KIND, "robotcomments").to(ListChangeRobotComments.class);
    get(CHANGE_KIND, "drafts").to(ListChangeDrafts.class);
    get(CHANGE_KIND, "check").to(Check.class);
    post(CHANGE_KIND, "check").to(Check.class);
    put(CHANGE_KIND, "topic").to(PutTopic.class);
    delete(CHANGE_KIND, "topic").to(PutTopic.class);
    delete(CHANGE_KIND).to(DeleteChange.class);
    post(CHANGE_KIND, "abandon").to(Abandon.class);
    post(CHANGE_KIND, "hashtags").to(PostHashtags.class);
    post(CHANGE_KIND, "publish").to(PublishDraftPatchSet.CurrentRevision.class);
    post(CHANGE_KIND, "restore").to(Restore.class);
    post(CHANGE_KIND, "revert").to(Revert.class);
    post(CHANGE_KIND, "submit").to(Submit.CurrentRevision.class);
    get(CHANGE_KIND, "submitted_together").to(SubmittedTogether.class);
    post(CHANGE_KIND, "rebase").to(Rebase.CurrentRevision.class);
    post(CHANGE_KIND, "index").to(Index.class);
    post(CHANGE_KIND, "rebuild.notedb").to(Rebuild.class);
    post(CHANGE_KIND, "move").to(Move.class);
    post(CHANGE_KIND, "private").to(PostPrivate.class);
    post(CHANGE_KIND, "private.delete").to(DeletePrivate.class);
    delete(CHANGE_KIND, "private").to(DeletePrivate.class);
    put(CHANGE_KIND, "ignore").to(Ignore.class);
    put(CHANGE_KIND, "unignore").to(Unignore.class);
    put(CHANGE_KIND, "mute").to(Mute.class);
    put(CHANGE_KIND, "unmute").to(Unmute.class);
    post(CHANGE_KIND, "wip").to(SetWorkInProgress.class);
    post(CHANGE_KIND, "ready").to(SetReadyForReview.class);

    post(CHANGE_KIND, "reviewers").to(PostReviewers.class);
    get(CHANGE_KIND, "suggest_reviewers").to(SuggestChangeReviewers.class);
    child(CHANGE_KIND, "reviewers").to(Reviewers.class);
    get(REVIEWER_KIND).to(GetReviewer.class);
    delete(REVIEWER_KIND).to(DeleteReviewer.class);
    post(REVIEWER_KIND, "delete").to(DeleteReviewer.class);
    child(REVIEWER_KIND, "votes").to(Votes.class);
    delete(VOTE_KIND).to(DeleteVote.class);
    post(VOTE_KIND, "delete").to(DeleteVote.class);

    child(CHANGE_KIND, "revisions").to(Revisions.class);
    get(REVISION_KIND, "actions").to(GetRevisionActions.class);
    post(REVISION_KIND, "cherrypick").to(CherryPick.class);
    get(REVISION_KIND, "commit").to(GetCommit.class);
    delete(REVISION_KIND).to(DeleteDraftPatchSet.class);
    get(REVISION_KIND, "mergeable").to(Mergeable.class);
    post(REVISION_KIND, "publish").to(PublishDraftPatchSet.class);
    get(REVISION_KIND, "related").to(GetRelated.class);
    get(REVISION_KIND, "review").to(GetReview.class);
    post(REVISION_KIND, "review").to(PostReview.class);
    get(REVISION_KIND, "preview_submit").to(PreviewSubmit.class);
    post(REVISION_KIND, "submit").to(Submit.class);
    post(REVISION_KIND, "rebase").to(Rebase.class);
    put(REVISION_KIND, "description").to(PutDescription.class);
    get(REVISION_KIND, "description").to(GetDescription.class);
    get(REVISION_KIND, "patch").to(GetPatch.class);
    get(REVISION_KIND, "submit_type").to(TestSubmitType.Get.class);
    post(REVISION_KIND, "test.submit_rule").to(TestSubmitRule.class);
    post(REVISION_KIND, "test.submit_type").to(TestSubmitType.class);
    get(REVISION_KIND, "archive").to(GetArchive.class);
    get(REVISION_KIND, "mergelist").to(GetMergeList.class);

    child(REVISION_KIND, "reviewers").to(RevisionReviewers.class);

    child(REVISION_KIND, "drafts").to(DraftComments.class);
    put(REVISION_KIND, "drafts").to(CreateDraftComment.class);
    get(DRAFT_COMMENT_KIND).to(GetDraftComment.class);
    put(DRAFT_COMMENT_KIND).to(PutDraftComment.class);
    delete(DRAFT_COMMENT_KIND).to(DeleteDraftComment.class);

    child(REVISION_KIND, "comments").to(Comments.class);
    get(COMMENT_KIND).to(GetComment.class);

    child(REVISION_KIND, "robotcomments").to(RobotComments.class);
    get(ROBOT_COMMENT_KIND).to(GetRobotComment.class);
    child(REVISION_KIND, "fixes").to(Fixes.class);
    post(FIX_KIND, "apply").to(ApplyFix.class);

    child(REVISION_KIND, "files").to(Files.class);
    put(FILE_KIND, "reviewed").to(PutReviewed.class);
    delete(FILE_KIND, "reviewed").to(DeleteReviewed.class);
    get(FILE_KIND, "content").to(GetContent.class);
    get(FILE_KIND, "download").to(DownloadContent.class);
    get(FILE_KIND, "diff").to(GetDiff.class);
    get(FILE_KIND, "blame").to(GetBlame.class);

    child(CHANGE_KIND, "edit").to(ChangeEdits.class);
    delete(CHANGE_KIND, "edit").to(DeleteChangeEdit.class);
    child(CHANGE_KIND, "edit:publish").to(PublishChangeEdit.class);
    child(CHANGE_KIND, "edit:rebase").to(RebaseChangeEdit.class);
    put(CHANGE_KIND, "edit:message").to(ChangeEdits.EditMessage.class);
    get(CHANGE_KIND, "edit:message").to(ChangeEdits.GetMessage.class);
    put(CHANGE_EDIT_KIND, "/").to(ChangeEdits.Put.class);
    delete(CHANGE_EDIT_KIND).to(ChangeEdits.DeleteContent.class);
    get(CHANGE_EDIT_KIND, "/").to(ChangeEdits.Get.class);
    get(CHANGE_EDIT_KIND, "meta").to(ChangeEdits.GetMeta.class);

    factory(AccountLoader.Factory.class);
    factory(ChangeEdits.Create.Factory.class);
    factory(ChangeEdits.DeleteFile.Factory.class);
    factory(ChangeInserter.Factory.class);
    factory(EmailReviewComments.Factory.class);
    factory(PatchSetInserter.Factory.class);
    factory(RebaseChangeOp.Factory.class);
    factory(ReviewerResource.Factory.class);
    factory(SetAssigneeOp.Factory.class);
    factory(SetHashtagsOp.Factory.class);
    factory(ChangeResource.Factory.class);
    factory(DeleteReviewerOp.Factory.class);
    factory(DeleteReviewerByEmailOp.Factory.class);
    factory(PostReviewersOp.Factory.class);
  }
}
