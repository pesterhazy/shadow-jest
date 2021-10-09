(ns todomvc.app-spec)

(js/console.log "XXX")

;; test('two plus two is four', () => {
;; expect(2 + 2).toBe(4);
;;});

#_(js/test "arithmetic"
           (fn []
             (-> (js/expect (+ 2 2))
                 (.toBe 5))))
