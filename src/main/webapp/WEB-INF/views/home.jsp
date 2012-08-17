<%@ page language="java" contentType="text/html; charsetLaunch=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>dispatch-test-spring-mvc</title>
</head>
<body>
  <h1>Home</h1>

  <h3>Callable Scenario</h3>
  <ul>
    <li><a href="callableScenario/a?end=viewName">End with JSP view</a>
    <li><a href="callableScenario/a?end=responseBody">End with @ResponseBody</a>
    <li><a href="callableScenario/a?callableHint=redirect">Redirect and end with JSP View</a> 
        (Tomcat issue <a href="https://issues.apache.org/bugzilla/show_bug.cgi?id=53624">53624</a>)
    <li><a href="callableScenario/a?callableHint=IllegalStateException">Raise <em>and resolve</em> Exception</a>
    <li><a href="callableScenario/a?callableHint=IllegalArgumentException">Raise <em>unhandled</em> Exception</a>
    <li><a href="callableScenario/a?callableHint=timeout">Timeout</a>
  </ul>

  <h3>Nested Callable Scenario</h3>
  <ul>
    <li><a href="nestedCallableScenario">Invoke 3 times</a>
  </ul>

  <h3>DeferredResult Scenario</h3>
  <p> and then click one of the links below to set the DeferredResult</p>
  <ul>
    <li><a href="deferredResultScenario/a?end=viewName">Start JSP view</a> <strong>then</strong> <a href="deferredResultScenario/ping" target="tab">ping to set</a>
    <li><a href="deferredResultScenario/a?end=responseBody">Start @ResponseBody</a> <strong>then</strong> <a href="deferredResultScenario/ping" target="tab">ping to set</a>
    <li><a href="deferredResultScenario/a">Start</a> <strong>then</strong> <a href="deferredResultScenario/ping?deferredResultHint=timeout" target="tab">ping and wait for timeout before set</a>
    <li><a href="deferredResultScenario/a?timeoutResult=page">Use default value on timeout</a>
  </ul>

</body>
</html>