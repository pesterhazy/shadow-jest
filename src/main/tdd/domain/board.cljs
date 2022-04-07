(ns tdd.domain.board
  (:require [tdd.board :as board]
            [tdd.game :as game]
            ["react" :as react]))

(defn board-do []
  (let [[game set-game] (react/useState (game/create))]
    [board/board-ui {:fs (game/fields game) ;; use accessor
                     :on-move (fn [n]
                                (set-game (fn [game] (game/move game n))))}]))
