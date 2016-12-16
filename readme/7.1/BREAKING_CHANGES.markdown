# What are the Breaking Changes for Liferay 7.1?

This document presents a chronological list of changes that break existing
functionality, APIs, or contracts with third party Liferay developers or users.
We try our best to minimize these disruptions, but sometimes they are
unavoidable.

Here are some of the types of changes documented in this file:

* Functionality that is removed or replaced
* API incompatibilities: Changes to public Java or JavaScript APIs
* Changes to context variables available to templates
* Changes in CSS classes available to Liferay themes and portlets
* Configuration changes: Changes in configuration files, like
  `portal.properties`, `system.properties`, etc.
* Execution requirements: Java version, J2EE Version, browser versions, etc.
* Deprecations or end of support: For example, warning that a certain
  feature or API will be dropped in an upcoming version.
* Recommendations: For example, recommending using a newly introduced API that
  replaces an old API, in spite of the old API being kept in Liferay Portal for
  backwards compatibility.

*This document has been reviewed through commit `24a4f24`.*

## Breaking Changes Contribution Guidelines

Each change must have a brief descriptive title and contain the following
information:

* **[Title]** Provide a brief descriptive title. Use past tense and follow
  the capitalization rules from
  <http://en.wikibooks.org/wiki/Basic_Book_Design/Capitalizing_Words_in_Titles>.
* **Date:** Specify the date you submitted the change. Format the date as
  *YYYY-MMM* (e.g., 2014-Mar) or *YYYY-MMM-DD* (e.g., 2014-Feb-25).
* **JIRA Ticket:** Reference the related JIRA ticket (e.g., LPS-123456)
  (Optional).
* **What changed?** Identify the affected component and the type of change that
  was made.
* **Who is affected?** Are end-users affected? Are developers affected? If the
  only affected people are those using a certain feature or API, say so.
* **How should I update my code?** Explain any client code changes required.
* **Why was this change made?** Explain the reason for the change. If
  applicable, justify why the breaking change was made instead of following a
  deprecation process.

Here's the template to use for each breaking change (note how it ends with a
horizontal rule):

```
### Title
- **Date:**
- **JIRA Ticket:**

#### What changed?

#### Who is affected?

#### How should I update my code?

#### Why was this change made?

---------------------------------------
```

**80 Columns Rule:** Text should not exceed 80 columns. Keeping text within 80
columns makes it easier to see the changes made between different versions of
the document. Titles, links, and tables are exempt from this rule. Code samples
must follow the column rules specified in Liferay's
[Development Style](http://www.liferay.com/community/wiki/-/wiki/Main/Liferay+development+style).

The remaining content of this document consists of the breaking changes listed
in ascending chronological order.

## Breaking Changes List

### Removed URL Parameters p_p_col_id, p_p_col_pos, and p_p_col_count from Every Portlet URL.
- **Date:** 2016-Dec-12
- **JIRA Ticket:** LPS-69482

#### What changed?

The parameters `p_p_col_count`, `p_p_col_id`, and `p_p_col_pos` are no longer
present in every portlet URL.

#### Who is affected?

This affects developers who are reading these parameters in their custom code.

#### How should I update my code?

You can no longer obtain these parameters from the portlet URL. If you need to
read them, you should do it from `PortletDisplay`.

- The parameter `p_p_col_count` can be obtained via the
  `portletDisplay.getColumnCount()` method.
- The parameter `p_p_col_id` can be obtained via the
  `portletDisplay.getColumnId()` method.
- The parameter `p_p_col_pos` can be obtained via the
  `portletDisplay.getColumnPos()` method.

#### Why was this change made?

This change simplifies portlet URLs so they only contain the required
parameters. This was done as a preliminary step of a bigger story to create
portlet URLs without passing the request as a necessary parameter.

---------------------------------------