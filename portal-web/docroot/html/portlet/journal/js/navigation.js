AUI.add(
	'liferay-journal-navigation',
	function(A) {
		var DISPLAY_STYLE_TOOLBAR = 'displayStyleToolbar';

		var STR_ROW_IDS_JOURNAL_FOLDER_CHECKBOX = 'rowIdsJournalFolder';

		var STR_ROW_IDS_JOURNAL_ARTICLE_CHECKBOX = 'rowIdsJournalArticle';

		var JournalNavigation = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'journalnavigation',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var journalContainer = instance.byId('journalContainer');

						instance._journalContainer = journalContainer;

						var checkBoxesId = [
							instance.ns(STR_ROW_IDS_JOURNAL_ARTICLE_CHECKBOX),
							instance.ns(STR_ROW_IDS_JOURNAL_FOLDER_CHECKBOX)
						];

						var displayStyle = A.clone(config.displayStyle);

						var displayStyleCSSClass = 'entry-display-style';

						var displayStyleToolbar = instance.byId(DISPLAY_STYLE_TOOLBAR);

						var namespace = instance.NS;

						var portletContainerId = instance.ns('journalContainer');

						var selectConfig = A.merge(
							config.select,
							{
								checkBoxesId: checkBoxesId,
								displayStyle: displayStyle,
								displayStyleCSSClass: displayStyleCSSClass,
								displayStyleToolbar: displayStyleToolbar,
								namespace: namespace,
								portletContainerId: portletContainerId,
								selector: 'entry-selector'
							}
						);

						instance._appViewSelect = new Liferay.AppViewSelect(selectConfig);

						var moveConfig = A.merge(
							config.move,
							{
								displayStyleCSSClass: displayStyleCSSClass,
								draggableCSSClass: '.entry-link',
								namespace: namespace,
								portletContainerId: portletContainerId,
								portletGroup: 'journal',
								processEntryIds: {
									checkBoxesIds: checkBoxesId,
									entryIds: [
										instance.ns('articleIds'),
										instance.ns('folderIds')
									]
								}
							}
						);

						instance._appViewMove = new Liferay.AppViewMove(moveConfig);
					},

					destructor: function() {
						var instance = this;

						instance._appViewMove.destroy();
						instance._appViewSelect.destroy();

						instance._journalContainer.purge(true);
					}
				}
			}
		);

		Liferay.Portlet.JournalNavigation = JournalNavigation;
	},
	'',
	{
		requires: ['liferay-app-view-move', 'liferay-app-view-select', 'liferay-portlet-base']
	}
);