# Liferay Gradle Plugins Baseline Change Log

## 1.1.0 - 2016-11-30

### Added
- [LPS-69470]: Add the property `forceCalculatedVersion` to all tasks that
extend `BaselineTask`. If `true`, the baseline check will fail if the
`Bundle-Version` has been excessively increased.

### Changed
- [LPS-69470]: Update the [Liferay Ant BND] dependency to version 2.0.31.

## 1.1.1 - 2017-01-04

### Changed
- [LPS-69899]: Update the [Liferay Ant BND] dependency to version 2.0.32.

## 1.1.2 - 2017-01-31

### Changed
- [LPS-70379]: Update the [Liferay Ant BND] dependency to version 2.0.33.

## 1.1.3 - 2017-03-15

### Changed
- [LPS-71118]: Update the [Liferay Ant BND] dependency to version 2.0.34.

[Liferay Ant BND]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/ant-bnd
[LPS-69470]: https://issues.liferay.com/browse/LPS-69470
[LPS-69899]: https://issues.liferay.com/browse/LPS-69899
[LPS-70379]: https://issues.liferay.com/browse/LPS-70379
[LPS-71118]: https://issues.liferay.com/browse/LPS-71118