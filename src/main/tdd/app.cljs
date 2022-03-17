(ns tdd.app
  (:require [tdd.infra.cmdline :as cmdline]))

(defn create [{:keys [cmdline]}]
  {:cmdline cmdline})

(defn run [{:keys [cmdline]}]
  (cmdline/write cmdline "hello"))
