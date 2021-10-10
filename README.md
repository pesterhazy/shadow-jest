# shadow-jest

![Screenshot](screenshot.png)

Shadow-jest is a demo app written for ClojureScript with shadow-cljs, Jest and the excellent [React Testing Library](https://testing-library.com/docs/react-testing-library/intro/). It is intended to demonstrate the feasibility of Test Driven Development (TDD) using component testing in ClojureScript.

Test Driven Development as [defined by Martin Fowler](https://martinfowler.com/bliki/TestDrivenDevelopment.html) means cycling through three steps:

- Write a test for the next bit of functionality you want to add.
- Write the functional code until the test passes.
- Refactor both new and old code to make it well structured.

Following TDD is generally hard to do well in frontend development but — as I'm slowly realizing, after many years — it's worth it because it tends to improve internal software quality and, just as importantly, it allows you to take [many more, much smaller steps](https://gist.github.com/pesterhazy/00ec5886e0378a83e5bf4ad96cfaaf65).

As a matter of fact, with the advent of React Testing Library (and previously Enzyme), component tests have now emerged as a significant category of tests alongside traditional microtests or unit tests on the one side and end-to-end tests on the other.

## Why Jest?

Jest is a state-of-the-art test runner, much better than anything available for ClojureScript. It makes integration with jsdom and React Testing Library easy and supports parallel test runs in node.

![Screenshot](jest.png)

It's often thought that ClojureScript should be tested by using tools that are native to the Clojure community. But using Jest, arguably JavaScript's most popular testing tool, allows you to take advantage of the many advances that frontend testing has made in the last decade, including:

- Great support for asynchronous tests
- Isolating test runs using `test.skip` and `test.only`
- Running code in node rather than the browser
- Fake timers and mocking support
- Integration with JSDOM
- Integration with React Testing Library

## Why UIx?

[UIx](https://github.com/roman01la/uix) is one of the modern React wrappers. As its syntax it uses Reagent-flavored hiccup, a.k.a. the magnificent sexp DSL that HTML could've been. It's modern because it exposes modern React primitives like useEffect, useState and useRef (which ClojureScript-land is only now catching up to).

## Usage

```
# Start the compilation
npm run dev

# Run the tests first - we're doing TDD after all
npm run test

# Optionally (!) check what it actually looks like in the browser
open http://localhost:8012/
```

## About TodoMVC

[TodoMVC](https://todomvc.com/) is, by convention, the Hello World 2.0 of frontend applications, and it's been around for years. The app has just enough state to warrant a framework like React. This kata brings us the joy of building a frontend app [with an existing specification](https://github.com/tastejs/todomvc/blob/master/app-spec.md) without worrying about the [CSS](https://unpkg.com/todomvc-app-css@2.4.1/index.css). We can focus only on the logic - and, of course, on the tests!

Check out [an example written in pure React](https://todomvc.com/examples/react/#/)

## Author

Paulus Esterhazy pesterhazy@gmail.com — feedback welcome!
