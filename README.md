# shadow-jest

![Screenshot](screenshot.png)

Shadow-jest is demo app written in ClojureScript using TDD, with shadow-cljs, Jest and the excellent React Testing Library.

1. Write production code only to pass a failing unit test

2. Write no more of a unit test than sufficient to fail

3. Write no more production code than necessary to pass the one failing unit test

## Why Jest?

Jest is a great test runner, much better than anything available for ClojureScript. It makes integration with jsdom and React Testing Library easy and supports parallel test runs in node.

![Screenshot](jest.png)

## Why UIx?

[UIx](https://github.com/roman01la/uix) is one of the modern React wrappers. As its syntax it uses Reagent-flavored hiccup, a.k.a. the DSL for HTML that's better than actual HTML. It's modern because it exposes modern React primitives like useEffect, useState and useRef.

## Usage

```
# Start the compilation
npm run dev

# Run the tests - we're doing TDD after all
npm run test

# Optionally (!) check what it actually looks like in the browser
open http://localhost:8012/
```

## About TodoMVC

[TodoMVC](https://todomvc.com/) is, by convention, the first demo app to build as a frontend application, and it's been around for years. It has just enough state to warrant a framework like React. It brings us the joy of building a frontend app [with an existing specification](https://github.com/tastejs/todomvc/blob/master/app-spec.md) without worrying about the [CSS](https://unpkg.com/todomvc-app-css@2.4.1/index.css).

Check out [an example written in pure React](https://todomvc.com/examples/react/#/)
