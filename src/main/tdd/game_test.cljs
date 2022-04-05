(ns tdd.game-test
  (:require
   ["@jest/globals" :refer [expect test]]
   [tdd.game]))

(test
 "returns 0 moves for empty game"
 (fn []
   (let [game (tdd.game/create)]
     (-> (expect (tdd.game/moves game))
         (.toBe 0)))))

(test
 "returns 0 moves for empty game"
 (fn []
   (let [game (-> (tdd.game/create)
                  (tdd.game/move 0))]
     (-> (expect (tdd.game/moves game))
         (.toBe 1)))))
