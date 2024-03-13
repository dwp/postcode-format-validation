<a name="2.0.0"></a>
# 2-0-0 (2024-03-06)

* Java upgraded to v17 and JUnit upgraded to v5 ([bbbbcccc](https://gitlab.com/dwp/health/shared-components/libraries/secure-strings/-/merge_requests/46/diffs?commit_id=change_this_id))

### [Breaking Change with Java17](https://spring.io/blog/2022/05/24/preparing-for-spring-boot-3-0)
Jakarta EE 9 a new top-level jakarta package, replacing EE 8’s javax top-level package. For example, the Servlet specification in Jakarta EE 8 uses a javax.servlet package but this has changed to jakarta.servlet in EE 9.

Generally speaking, it’s not possible to mix Java EE and Jakarta EE APIs in the same project. You need to ensure that your own code, as well as all third-party libraries are using jakarta.* package imports.

<a name="1-3-1"></a>
# 1-3-1 (2023-05-10)

* Adding 'renovate.json' to the default branch via automation ([25cb922](https://gitlab.com/dwp/health/shared-components/libraries/postcode-format-validation/-/commit/25cb922))
* chore: update pipeline fragments ([839d97b](https://gitlab.com/dwp/health/shared-components/libraries/postcode-format-validation/-/commit/839d97b))
* chore: bump snapshot version [skip ci] ([647686d](https://gitlab.com/dwp/health/shared-components/libraries/postcode-format-validation/-/commit/647686d))
