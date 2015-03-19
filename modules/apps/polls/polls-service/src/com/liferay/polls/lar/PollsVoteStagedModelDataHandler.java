/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.polls.lar;

import com.liferay.polls.exception.DuplicateVoteException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shinn Lok
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class PollsVoteStagedModelDataHandler
	extends BaseStagedModelDataHandler<PollsVote> {

	public static final String[] CLASS_NAMES = {PollsVote.class.getName()};

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		PollsVote vote = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (vote != null) {
			PollsVoteLocalServiceUtil.deletePollsVote(vote);
		}
	}

	@Override
	public PollsVote fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return PollsVoteLocalServiceUtil.fetchPollsVoteByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<PollsVote> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return PollsVoteLocalServiceUtil.getPollsVotesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<PollsVote>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, PollsVote vote)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, vote, vote.getChoice(),
			PortletDataContext.REFERENCE_TYPE_STRONG);

		Element voteElement = portletDataContext.getExportDataElement(vote);

		portletDataContext.addClassedModel(
			voteElement, ExportImportPathUtil.getModelPath(vote), vote);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long voteId)
		throws Exception {

		PollsVote existingVote = fetchMissingReference(uuid, groupId);

		Map<Long, Long> voteIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsVote.class);

		voteIds.put(voteId, existingVote.getVoteId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, PollsVote vote)
		throws Exception {

		Map<Long, Long> questionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsQuestion.class);

		long questionId = MapUtil.getLong(
			questionIds, vote.getQuestionId(), vote.getQuestionId());

		Map<Long, Long> choiceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsChoice.class);

		long choiceId = MapUtil.getLong(
			choiceIds, vote.getChoiceId(), vote.getChoiceId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			vote);

		serviceContext.setCreateDate(vote.getVoteDate());

		if (portletDataContext.isDataStrategyMirror()) {
			PollsVote existingVote = fetchStagedModelByUuidAndGroupId(
				vote.getUuid(), portletDataContext.getScopeGroupId());

			if (existingVote == null) {
				serviceContext.setUuid(vote.getUuid());
			}
		}

		try {
			PollsVoteLocalServiceUtil.addVote(
				vote.getUserId(), questionId, choiceId, serviceContext);
		}
		catch (DuplicateVoteException dve) {
		}
	}

}