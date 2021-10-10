# shadow-jest

What is shadow-jest? It's TodoMVC written in ClojureScript using TDD, with shadow-cljs, Jest and the excellent React Testing Library.

1. Write production code only to pass a failing unit test

2. Write no more of a unit test than sufficient to fail

3. Write no more production code than necessary to pass the one failing unit test

# Why Jest?

Rationale: Jest is a much better test runner than anything available for ClojureScript. It makes integration with jsdom and React Testing Library easy and supports parallel test runs in node.

# Usage

```
npm run dev
npm run test
open http://localhost:8012/
```

# About TodoMVC

[TodoMVC](https://todomvc.com/) is, by convention, the first demo app to build as a frontend application, and it's been around for years. It has just enough state to warrant a framework like React. It brings us the joy of building a frontend app [with an existing specification](https://github.com/tastejs/todomvc/blob/master/app-spec.md) without worrying about the [CSS](https://unpkg.com/todomvc-app-css@2.4.1/index.css).

Check out [an example written in pure React](https://todomvc.com/examples/react/#/)
