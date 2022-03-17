(ns tdd.main
  (:require [tdd.infra.cmdline :as cmdline]))

(let [cmdline (cmdline/create)]
  (cmdline/write cmdline "hello"))
