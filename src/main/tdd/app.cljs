(ns tdd.app
  (:require [tdd.infra.cmdline :as cmdline]
            [tdd.logic.rot13 :as rot13]))

(defn create [{:keys [cmdline]}]
  {:cmdline cmdline})

(defn run [{:keys [cmdline]}]
  (let [args (cmdline/args cmdline)]
    (cond
      (< (count args) 1)
      (cmdline/write cmdline "Too few args")
      (> (count args) 1)
      (cmdline/write cmdline "Too many args")
      :else
      (cmdline/write cmdline (rot13/rot13 (first args))))))
