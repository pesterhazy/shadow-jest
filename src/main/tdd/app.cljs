(ns tdd.app
  (:require [tdd.infra.cmdline :as cmdline]
            [tdd.logic.rot13 :as rot13]))

(defn create [{:keys [cmdline]}]
  {:cmdline cmdline})

(defn run [{:keys [cmdline]}]
  (cmdline/write cmdline (rot13/rot13 (first (cmdline/args cmdline)))))
