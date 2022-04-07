(ns tdd.domain.board
  (:require [tdd.ui.board :as board]
            [tdd.logic.game :as game]
            ["react" :as react]))

(defn board-do
  ([] (board-do {}))
  ([{:keys [game]}]
   (let [[game set-game] (react/useState (or game (game/create)))
         result (game/result game)]
     [:div
      [board/board-ui {:fs (game/fields game) ;; use accessor
                       :on-move (fn [n]
                                  (set-game (fn [game] (game/move game n))))}]
      (case result
        (:X :O)
        [:div "Player " (name result) " won"]
        :draw
        [:div "Draw"]
        nil)])))
