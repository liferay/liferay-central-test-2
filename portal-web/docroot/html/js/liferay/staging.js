AUI().add(
	'staging',
	function(A) {
		var Staging = {};

		var Branching = {
			init: function(options) {
				var instance = this;

				instance._namespace = options.namespace;
			},

			addBranch: function() {
				var instance = this;

				var dialog = instance._addBranchDialog;

				if (!dialog) {
					var content = A.one('#addBranch');

					dialog = new A.Dialog(
						{
							align: {
								node: null,
								points: ['tc', 'tc']
							},
							bodyContent: content.show(),
							title: Liferay.Language.get('add-branch'),
							modal: true,
							width: 530
						}
					).render();

					dialog.move(dialog.get('x'), dialog.get('y') + 100);

					instance._addBranchDialog = dialog;
				}

				dialog.show();
			},

			mergeBranch: function(options) {
				var instance = this;

				var groupId = options.groupId;
				var layoutSetBranchId = options.layoutSetBranchId;
				var privateLayout = options.privateLayout;

				var url = themeDisplay.getPathMain() + '/group_pages/merge_branch?groupId=' + groupId + '&layoutSetBranchId=' + layoutSetBranchId + '&privateLayout=' + privateLayout;

				var dialog = instance._mergeBranchDialog;

				if (!dialog) {
					dialog = new A.Dialog(
						{
							align: {
								node: null,
								points: ['tc', 'tc']
							},
							draggable: true,
							modal: true,
							title: Liferay.Language.get('merge-changes-from-branch'),
							width: 530
						}
					).plug(
						A.Plugin.IO,
						{
							autoLoad:false,
							data: {
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								p_l_id: themeDisplay.getPlid(),
								redirect: Liferay.currentURL
							},
							uri: url
						}
					).render();

					dialog.move(dialog.get('x'), dialog.get('y') + 100);

					dialog.bodyNode.delegate(
						'click',
						instance._mergeBranch,
						'a.layout-set-branch'
					);

					instance._mergeBranchDialog = dialog;
				}

				dialog.io.set('uri', url);
				dialog.io.start();
				dialog.show();
			},

			_mergeBranch: function(event) {
				var instance = this;

				var node = event.currentTarget;

				var addBranch = A.one('#addBranch');

				var namespace = addBranch.attr('data-namespace');

				var mergeLayoutSetBranchId = node.attr('data-layoutSetBranchId');
				var mergeLayoutSetBranchName = node.attr('data-layoutSetBranchName');

				if (confirm(Liferay.Language.get('are-you-sure-you-want-to-merge-changes-from-branch') + ' ' + mergeLayoutSetBranchName)) {
					window.document[namespace + 'fm4'][namespace + 'mergeLayoutSetBranchId'].value = mergeLayoutSetBranchId;

					window.submitForm(window.document[namespace + 'fm4']);
				}
			}
		};

		var Dockbar = {
			init: function() {
				var instance = this;

				var dockBar = A.one('#stagingDockbar');

				if (dockBar) {
					instance.dockBar = dockBar;

					instance._namespace = dockBar.attr('data-namespace');
				}

				var namespace = instance._namespace;

				var selectBranch = A.one('#' + instance._namespace + 'layoutSetBranchId');

				if (selectBranch) {
					selectBranch.on(
						'change',
						function(event) {
							if (confirm(Liferay.Language.get('are-you-sure-you-want-to-switch-to-another-branch'))) {
								window.document[namespace + 'fm'][namespace + 'cmd'].value = "select_layout_set_branch";

								window.submitForm(window.document[namespace + 'fm']);
							}
						}
					);
				}

				var undoRevision = A.one('#undoRevision');

				if (undoRevision) {
					undoRevision.on(
						'click',
						function(event) {
							if (confirm(Liferay.Language.get('are-you-sure-you-want-to-undo-your-last-changes'))) {
								window.document[namespace + 'fm'][namespace + 'cmd'].value = "delete_layout_revision";
								window.document[namespace + 'fm'][namespace + 'updateSessionClicks'].value = true;

								window.submitForm(window.document[namespace + 'fm']);
							}
						}
					);
				}

				var viewHistory = A.one('#viewHistory');

				if (viewHistory) {
					viewHistory.on(
						'click',
						function(event) {
							var layoutRevisionId = window.document[namespace + 'fm'][namespace + 'layoutRevisionId'].value;

							var layoutSetBranchIdSelect = window.document[namespace + 'fm'][namespace + 'layoutSetBranchId'];

							var layoutSetBranchId = layoutSetBranchIdSelect.options[layoutSetBranchIdSelect.selectedIndex].value;

							var url = themeDisplay.getPathMain() + '/dockbar/revision_graph?layoutRevisionId=' + layoutRevisionId + '&layoutSetBranchId=' + layoutSetBranchId;

							var dialog = instance._graphDialog;

							if (!dialog) {
								dialog = new A.Dialog(
									{
										align: {
											node: null,
											points: ['tc', 'tc']
										},
										draggable: true,
										modal: true,
										title: Liferay.Language.get('history'),
										width: 400
									}
								).plug(
									A.Plugin.IO,
									{
										autoLoad:false,
										data: {
											doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
											p_l_id: themeDisplay.getPlid(),
											redirect: Liferay.currentURL
										},
										uri: url
									}
								).render();

								dialog.move(dialog.get('x'), dialog.get('y') + 100);

								dialog.bodyNode.delegate(
									'click',
									instance._selectRevision,
									'li.layout-revision a.selection-handle'
								);

								instance._graphDialog = dialog;
							}

							dialog.io.set('uri', url);
							dialog.io.start();
							dialog.show();
						}
					);
				}
			},

			_selectRevision : function(event) {
				var node = event.currentTarget;

				A.io.request(
					themeDisplay.getPathMain() + '/portal/update_layout',
					{
						data: {
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							p_l_id: themeDisplay.getPlid(),
							cmd: 'select_layout_revision',
							layoutRevisionId: node.attr('data-layoutRevisionId'),
							layoutSetBranchId: node.attr('data-layoutSetBranchId')
						},
						on: {
							success: function(event, id, obj) {
								window.location.reload();
							}
						}
					}
				);
			},

			_updateMajor: function() {
				if (confirm(Liferay.Language.get('are-you-sure-you-want-to-save-your-changes-all-the-undo-steps-will-be-lost'))) {
					window.document[namespace + 'fm'][namespace + 'cmd'].value = "update_major";

					window.submitForm(window.document[namespace + 'fm']);
				}
			}
		};

		Staging.Branching = Branching;
		Staging.Dockbar = Dockbar;
		Liferay.Staging = Staging;
	},
	'',
	{
		requires: ['aui-dialog', 'aui-io-plugin', 'aui-io-request', 'liferay-portlet-url']
	}
);