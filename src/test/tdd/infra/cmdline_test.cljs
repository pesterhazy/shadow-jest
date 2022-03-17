(ns tdd.infra.cmdline-test
  (:require [tdd.infra.cmdline :as x]))

(js/test
 "creates nullable cmdline"
 (fn []
   (let [cmdline (x/create-null)]
     (x/write cmdline "foo")
     (-> (js/expect (x/last-output cmdline))
         (.toBe "foo\n")))))
