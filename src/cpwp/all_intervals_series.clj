(ns cpwp.all-intervals-series
  (:use [cpwp.core])
  (:import [javax.constraints 
            Problem
            ProblemFactory
            Var])
  (:import [jm.music.data
            Score            
            Part
            Phrase
            Note])
  (:import [jm JMC])
  (:import [jm.util Write]))

(def problem (ProblemFactory/newProblem "All Intervals Series"))

(def l 12)

(defn make-list
  [prefix l min max]
  (let [?list '()]
  (for [i (range l)]
    (cond ?list (.variable problem (str prefix i) min max)))))

(defn solution
  []
  (let [xs  (make-list "xs"  l 0 (dec l))
        dxs (make-list "dxs" (dec l) 1 (dec l))]           
    (.postAllDifferent problem xs)
    (.postAllDifferent problem dxs)
    
    (dotimes [i (dec l)]
      (when (> i 0)        
        (.post problem (.mod (.plus (.minus (nth xs (inc i))
                                      (nth xs i)) l) l) "=" (nth dxs i))))
  
    (.post problem (first xs) "=" 0)
    (.post problem (last  xs) "=" (quot l 2))))
    

(defn make-music
  [next-solution]
  (let [score      (new Score "Mx Demo - All Intervals Series")
        piano-part (new Part "Piano" JMC/PIANO 0)        
        phrase     (new Phrase "All Intervals Series" 0.0)
        notes      (map #(.getValue next-solution (str "xs" %)) (range (dec l)))]            
    
    (doseq [note notes]
      (.addNote phrase (new Note (+ note 60) JMC/C))) ;(60 = c4)          
    
    (.addPhrase piano-part phrase)
    (.addPart   score piano-part)
    
    (Write/midi score (str "/Google Drive/tmp/Mx/all-intervals-series" 
                           next-solution ".mid"))))


(solve-midi problem solution make-music)