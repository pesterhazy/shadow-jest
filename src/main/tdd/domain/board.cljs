(ns tdd.domain.board
  (:require [tdd.ui.board :as board]
            [tdd.logic.game :as game]
            ["react" :as react]))

(defn board-do
  ([] (board-do {}))
  ([{initial-game :game}]
   (let [[game set-game] (react/useState (or initial-game (game/create)))
         result (game/result game)]
     [board/board-ui {:fs (game/fields game) ;; use accessor
                      :frozen (not= :pending result)
                      :highlight (game/winners game)
                      :on-move (fn [n]
                                 (set-game (fn [game] (game/advance game n))))}
      [board/result-ui {:result result
                        :on-restart (fn []
                                      (set-game (game/create)))}]])))
