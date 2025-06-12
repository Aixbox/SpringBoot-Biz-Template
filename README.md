参考ruoyi-vue-pro和ruoyi-vue-plus从零搭建一个项目











# commit emoji 图标

**Inspired by** [dannyfritz/commit-message-emoji](https://github.com/dannyfritz/commit-message-emoji)

**See also** [gitmoji](https://gitmoji.carloscuesta.me/).


| **Commit type**                | **Emoji**                                        |
| ------------------------------ | ------------------------------------------------ |
| **Initial commit**             | **🎉** `:tada:`                                  |
| **Version tag**                | **🔖** `:bookmark:`                              |
| **New feature**                | **✨** `:sparkles:`                              |
| **Bugfix**                     | **🐛** `:bug:`                                   |
| **Metadata**                   | **📇** `:card_index:`                            |
| **Documentation**              | **📚** `:books:`                                 |
| **Documenting source code**    | **💡** `:bulb:`                                  |
| **Performance**                | **🐎** `:racehorse:`                             |
| **Cosmetic**                   | **💄** `:lipstick:`                              |
| **Tests**                      | **🚨** `:rotating_light:`                        |
| **Adding a test**              | **✅** `:white_check_mark:`                      |
| **Make a test pass**           | **✔️** `:heavy_check_mark:`                    |
| **General update**             | **⚡️** `:zap:`                                 |
| **Improve format/structure**   | **🎨** `:art:`                                   |
| **Refactor code**              | **🔨** `:hammer:`                                |
| **Removing code/files**        | **🔥** `:fire:`                                  |
| **Continuous Integration**     | **💚** `:green_heart:`                           |
| **Security**                   | **🔒** `:lock:`                                  |
| **Upgrading dependencies**     | **⬆️** `:arrow_up:`                            |
| **Downgrading dependencies**   | **⬇️** `:arrow_down:`                          |
| **Lint**                       | **👕** `:shirt:`                                 |
| **Translation**                | **👽** `:alien:`                                 |
| **Text**                       | **📝** `:pencil:`                                |
| **Critical hotfix**            | **🚑** `:ambulance:`                             |
| **Deploying stuff**            | **🚀** `:rocket:`                                |
| **Fixing on MacOS**            | **🍎** `:apple:`                                 |
| **Fixing on Linux**            | **🐧** `:penguin:`                               |
| **Fixing on Windows**          | **🏁** `:checkered_flag:`                        |
| **Work in progress**           | **🚧** `:construction:`                          |
| **Adding CI build system**     | **👷** `:construction_worker:`                   |
| **Analytics or tracking code** | **📈** `:chart_with_upwards_trend:`              |
| **Removing a dependency**      | **➖** `:heavy_minus_sign:`                      |
| **Adding a dependency**        | **➕** `:heavy_plus_sign:`                       |
| **Docker**                     | **🐳** `:whale:`                                 |
| **Configuration files**        | **🔧** `:wrench:`                                |
| **Package.json in JS**         | **📦** `:package:`                               |
| **Merging branches**           | **🔀** `:twisted_rightwards_arrows:`             |
| **Bad code / need improv.**    | **💩** `:hankey:`                                |
| **Reverting changes**          | **⏪** `:rewind:`                                |
| **Breaking changes**           | **💥** `:boom:`                                  |
| **Code review changes**        | **👌** `:ok_hand:`                               |
| **Accessibility**              | **♿️** `:wheelchair:`                          |
| **Move/rename repository**     | **🚚** `:truck:`                                 |
| **Other**                      | [Be creative](http://www.emoji-cheat-sheet.com/) |

# 数据库表基础字段

```sql
`creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',

```
