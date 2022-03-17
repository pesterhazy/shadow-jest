const {pass, fail} = require("jest-extended");
expect.extend({pass,fail});

const {toEq} = require("./dist-test/jest_cljs.matchers");
expect.extend({toEq});
