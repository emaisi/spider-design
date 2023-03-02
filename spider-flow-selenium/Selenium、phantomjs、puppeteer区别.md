## phantomjs、puppeteer区别
1、phantomjs、puppeteer和是类似工具，都是一个无界面的浏览器。

2、phantomjs几年前已经不维护更新，才有后面https://github.com/puppeteer/puppeteer。

3、puppeteer能通过api

## Selenium 和 Puppeteer区别

1、Puppeteer 是一个完整的 Chrome 自动化解决方案。使用 Puppeteer 的主要优势在于它可以访问 DevTools 协议和控制 Chrome。

2、Selenium 需要更复杂的安装来考虑所有模块以及您正在使用的特定浏览器和语言。Puppeteer 运行速度极快，而 Selenium 需要 WebDriver 将脚本命令发送到浏览器。

3、Puppeteer 提供了重要的性能管理功能，例如记录运行时和负载性能、捕获屏幕截图，甚至限制 CPU 性能以模拟移动设备上的性能。Selenium 不提供这样的性能管理功能。

4、Selenium 是一个专门用于测试在不同操作系统（Windows、Linux 和 Mac OS）上运行于多个浏览器（Chrome、Firefox、Safari 等）的应用程序的解决方案。许多 Web 应用程序无法规定用户必须使用哪些浏览器。因此，开发人员必须针对多种浏览器测试他们的应用程序。

5、Selenium IDE 用于编写 Selenium 测试脚本和套件。它支持记录测试脚本，极大地提高了测试人员的工作效率。另一方面，与 Puppeteer 的 Node.js 包方法相比，Selenium IDE 和 Selenese 是开发人员需要学习的另一组工具和语言。

6、Selenium Grid 管理 Selenium 测试在多台机器/浏览器上的执行。这允许在多个浏览器和平台上执行一项测试。测试套件的并行执行减少了完成应用程序测试所需的时间。


## Selenium 和 Puppeteer 之间的选择归结为您的需求。

如果您的主要重点是测试浏览器应用程序，尤其是在多个浏览器上，那么 Selenium 是一个更好的选择。它专为跨平台测试而构建。

如果你只专注于 Chrome 和 JavaScript，Puppeteer 更合适。
