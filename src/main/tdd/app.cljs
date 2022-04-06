(ns tdd.app
  (:require [tdd.board :as board]))

(defn app-ui []
  (let [fs board/empty-fs]
    [board/board-ui {:fs fs}]))
