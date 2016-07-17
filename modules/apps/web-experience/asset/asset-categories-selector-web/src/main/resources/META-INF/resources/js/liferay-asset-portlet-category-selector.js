AUI.add(
	'liferay-asset-portlet-category-selector',
	function(A) {
		var Lang = A.Lang;

		var LString = Lang.String;

		var STR_START = 'start';

		var NAME = 'assetPortletCategorySelector';

		var AssetPortletCategorySelector = A.Component.create(
			{
				ATTRS: {
					boundingBox: {
						value:''
					},
					entries: {
						value: {}
					},
					entryIds : {
						value: ''
					},
					eventName: {
						value: ''
					},
					namespace: {
						value: ''
					},
					singleSelect: {
						value: false
					},
					url : {
						value: ''
					},
					vocabularyRootNode: {
						value: {}
					}
				},

				NAME: NAME,

				prototype: {
					renderUI: function() {
						this.entryIdsArr = this.get('entryIds').split(',');

						this.treeView = new A.TreeView(
							{
								boundingBox: this.get('boundingBox'),
								children: [this.get('vocabularyRootNode')],
								io: {
									cfg: {
										data: this.formatRequestData.bind(this),
									},
									formatter: this.formatJSONResult.bind(this),
									url: this.get('url')
								}
							}
						).render();
					},

					clearEntries: function() {
						this.entries = {};
					},

					formatJSONResult: function(json) {
						var output = [];

						var type = 'check';

						if (json.length) {
							json.forEach(
								function(item, index) {
									var treeId = 'category' + item.categoryId;

									var newTreeNode = {
										after: {
											checkedChange: function(event) {
												if (event.newVal) {
													if (this.get('singleSelect')) {
														type = 'radio';
													}

													this.onCheckboxCheck(event);

													Liferay.Util.getOpener().Liferay.fire(
														this.get('eventName'),
														{
															data: {
																items: this.entries
															}
														}
													);
												}
												else {
													this.onCheckboxUncheck(event);
												}
											}.bind(this)
										},
										checked: false,
										id: treeId,
										label: LString.escapeHTML(item.titleCurrentValue),
										leaf: !item.hasChildren,
										paginator: this.getPaginatorConfig(item),
										type: type,
										parentCategoryIds: item.parentCategoryIds
									};


									if (this.entryIdsArr.indexOf(item.categoryId) !== -1) {
										this.entries = this.get('entries');
										this.entries[item.categoryId] = item;
										newTreeNode.checked = true;
										newTreeNode.expanded = true;
									}

									output.push(newTreeNode);

								}.bind(this)
							);
						}

						return output;
					},

					formatRequestData: function(treeNode) {
						var assetId = this.getTreeNodeAssetId(treeNode);

						var assetType = this.getTreeNodeAssetType(treeNode);

						var url = this.get('url');

						if (Lang.isValue(assetId) && assetType === 'category') {
							var urlGetSubCategories = url;
							urlGetSubCategories = Liferay.Util.addParams(this.get('namespace') + 'categoryId=' + assetId, urlGetSubCategories);
							var io = treeNode.get('io');
							io.url = urlGetSubCategories;
							treeNode.set('io', io);
						}
					},

					getPaginatorConfig: function(item) {
						var paginatorConfig = {
							offsetParam: STR_START
						};

						var maxEntries = "maxEntries";

						if (maxEntries > 0) {
							paginatorConfig.limit = maxEntries;
							paginatorConfig.moreResultsLabel = 'moreResultsLabel';
							paginatorConfig.total = item.childrenCount;
						}
						else {
							paginatorConfig.end = -1;
							paginatorConfig.start = -1;
						}

						paginatorConfig.total = item.childrenCount;

						return paginatorConfig;
					},

					getTreeNodeAssetId: function(treeNode) {
						var treeId = treeNode.get('id');

						var match = treeId.match(/(\d+)$/);

						return match ? match[1] : null;
					},

					getTreeNodeAssetType: function(treeNode) {
						var treeId = treeNode.get('id');

						var match = treeId.match(/^(vocabulary|category)/);

						return match ? match[1] : null;
					},

					onCheckboxCheck: function(event) {
						var currentTarget = event.currentTarget;

						var assetId;

						var entryMatchKey;

						var entries = this.get('entries');

						if (A.instanceOf(currentTarget, A.Node)) {
							assetId = currentTarget.attr('data-categoryId');

							entryMatchKey = currentTarget.val();
						}
						else {
							assetId = this.getTreeNodeAssetId(currentTarget);

							entryMatchKey = currentTarget.get('label');
						}

						var entry = {
							categoryId: assetId
						};

						entry.value = entryMatchKey;

						entry.value = LString.unescapeHTML(entry.value);

						entries[assetId] = entry;

						this.entries = entries;
					},

					onCheckboxUncheck: function(event) {
						var currentTarget = event.currentTarget;

						var assetId;

						if (A.instanceOf(currentTarget, A.Node)) {
							assetId = currentTarget.attr('data-categoryId');
						}
						else {
							assetId = this.getTreeNodeAssetId(currentTarget);
						}

						delete this.entries[assetId];
					}
				}
			}
		);

		Liferay.AssetPortletCategorySelector = AssetPortletCategorySelector;
	},
	'',
	{
		requires: ['aui-tree-view']
	}
);