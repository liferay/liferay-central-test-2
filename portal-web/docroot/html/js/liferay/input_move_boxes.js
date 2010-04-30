AUI().add(
	'liferay-input-move-boxes',
	function(A) {
		var InputMoveBoxes = function(options) {
			var instance = this;

			instance._container = A.one(options.container);

			instance._leftReorder = instance._container.hasClass('left-reorder');
			instance._rightReorder = instance._container.hasClass('right-reorder');

			var leftBox = instance._container.one('.left-selector select');
			var rightBox = instance._container.one('.right-selector select');

			if(leftBox && rightBox) {
				leftBox.on(
					'focus',
					function(event) {
						rightBox.set('selectedIndex', '-1');
					}
				);

				rightBox.on(
					'focus',
					function(event) {
						leftBox.set('selectedIndex', '-1');
					}
				);

				var arrowButtonLeftReorderUp = instance._container.one('a.left-reorder-up');
				var arrowButtonLeftReorderDown = instance._container.one('a.left-reorder-down');

				if(arrowButtonLeftReorderUp && arrowButtonLeftReorderDown) {
					arrowButtonLeftReorderUp.on(
						'click',
						function(event) {
							alert('up');
							Liferay.Util.reorder(leftBox, 0);
						}
					);

					arrowButtonLeftReorderDown.on(
						'click',
						function(event) {
							Liferay.Util.reorder(leftBox, 1);
						}
					);

				}

				var leftReorder = new A.Toolbar(
					{
						activeState: true,
						children: [
							{icon: 'circle-arrow-t'},
							{icon: 'circle-arrow-b'},
						]
					}
				).render('#demo3');

				var arrowButtonRightReorderUp = instance._container.one('a.right-reorder-up');
				var arrowButtonRightReorderDown = instance._container.one('a.right-reorder-down');

				if(arrowButtonRightReorderUp && arrowButtonRightReorderDown) {
					arrowButtonRightReorderUp.on(
						'click',
						function(event) {
							Liferay.Util.reorder(rightBox, 0);
						}
					);

					arrowButtonRightReorderDown.on(
						'click',
						function(event) {
							Liferay.Util.reorder(rightBox, 1);
						}
					);

				}

				var arrowButtonLeftMove = instance._container.one('a.left-move');
				var arrowButtonRightMove = instance._container.one('a.right-move');

				if(arrowButtonLeftMove && arrowButtonRightMove) {
					arrowButtonLeftMove.on(
						'click',
						function(event) {
							Liferay.Util.moveItem(leftBox, rightBox, !instance._rightReorder);
						}
					);

					arrowButtonRightMove.on(
						'click',
						function(event) {
							Liferay.Util.moveItem(rightBox, leftBox, !instance._leftReorder);
						}
					);

				}

			}

		};
		Liferay.InputMoveBoxes = InputMoveBoxes;

	},
	'',
	{
		requires: ['aui-base', 'aui-toolbar']
	}
);