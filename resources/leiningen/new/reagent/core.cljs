(ns {{namespace}}
  (:require [reagent.core :as r]))

(defonce timer
  (r/atom (js/Date.)))

(defonce time-color
  (r/atom "red"))

(defonce time-updater
  (js/setInterval
   (fn []
     (reset! timer (js/Date.)))
   1000))

(defn greeting [message]
  [:h1 message])

(defn add-hours
  "Adds hours (number) to date."
  [date hours]
  (let [current-hours (.getHours date)]
    (doto date
      (.setHours (+ current-hours hours)))))

(defn clock [city time-str]
  [:div.example-clock
   [:div (str city ": ")]
   [:div
    {:style {:color @time-color}}
    time-str]])

(defn color-input []
  [:div.color-input
   "Time color: "
   [:input {:type "text"
            :value @time-color
            :on-change (fn [event]
                         (reset! time-color (-> event .-target .-value)))}]])

(def time-differences
  {"Amsterdam" 0
   "Paris" 0
   "London" -1
   "Abu Dhabi" 3
   "St Petersburg" 2
   "Shanghai" 7
   "Minneapolis" -7
   "Montreal" -6
   "Glasgow" -1})

(defn make-time-str [datetime]
  (-> datetime
      .toTimeString
      (clojure.string/split " ")
      first))

(def flag-urls
  ["https://upload.wikimedia.org/wikipedia/commons/thumb/7/75/Flag_of_Paris_with_coat_of_arms.svg/440px-Flag_of_Paris_with_coat_of_arms.svg.png"
   "https://upload.wikimedia.org/wikipedia/en/thumb/c/c3/Flag_of_France.svg/60px-Flag_of_France.svg.png"])

(defn flag-img [url]
  [:img {:src url}])

;; Use for in function below to show all flags using flag-img and the flag-urls.
(defn flag-images [])

(defn page []
  [:div
   [greeting "Hello world, it is now"]
   (for [[city time-diff] time-differences]
     (let [time-str (-> @timer
                        (add-hours time-diff)
                        make-time-str)]
       [clock city time-str]))
   [color-input]
   [flag-images]])

(r/render-component [page] (.querySelector js/document "#content"))
