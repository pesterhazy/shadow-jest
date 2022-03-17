(ns tdd.main
  (:require [tdd.app :as app]
            [tdd.infra.cmdline :as cmdline]))

(app/run {:cmdline (cmdline/create)})
