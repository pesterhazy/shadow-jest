(ns tdd.infra.cmdline-test
  (:require [tdd.infra.cmdline :as x]
            jest.matchers))

(js/test
 "writes output"
 (fn []
   (let [cmdline (x/create-null)]
     (x/write cmdline "foo")
     (-> (js/expect (x/last-output cmdline))
         (.toBe "foo\n")))))

(js/test
 "returns empty args"
 (fn []
   (let [cmdline (x/create-null)]
     (-> (js/expect (x/args cmdline))
         (.toEq [])))))

(js/test
 "gets args"
 (fn []
   (let [cmdline (x/create-null {:args ["a" "b" "c"]})]
     (-> (js/expect (x/args cmdline))
         (.toEq ["a" "b" "c"])))))
