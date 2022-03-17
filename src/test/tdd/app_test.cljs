(ns tdd.app-test
  (:require [tdd.app :as x]
            [jest.matchers]))

(js/test
 "dummy"
 (fn [] (-> (js/expect 1) (.toBe 1))))
