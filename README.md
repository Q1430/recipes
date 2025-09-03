项目描述： 该应用旨在解决用户的日常烹饪选择困难问题。通过调用 TheMealDB 公开 API，为用户提供随机菜谱发现、
根据持有食材搜索菜谱，以及个人菜谱收藏管理等核心功能。项目采用业界前沿的技术栈，实现了高度模块化、
可测试和可维护的应用架构，项目严格遵循Clean Architecture，目前仅简单实现了随机食谱和收藏功能，
后续会开发根据已有食材推荐食谱和其他功能，有机会的话会考虑做分享等功能。目前ui较为简陋......（能用就行了😋）
技术栈：
核心架构: MVVM, Clean Architecture (分层：Presentation, Domain, Data), Repository 设计模式, Use Cases
UI: Jetpack Compose, Material 3, Navigation Compose (单 Activity 导航)
异步处理: Kotlin Coroutines, Flow (用于响应式数据流)
依赖注入: Hilt
网络请求: Retrofit, OkHttp (及 HttpLoggingInterceptor), Gson
本地存储: Room Database
图片加载: Coil
