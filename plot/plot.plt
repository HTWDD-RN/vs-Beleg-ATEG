set encoding utf8
set term qt size 1000,600 font "Arial,10"
set title "Rechenzeit in Abhängigkeit von Thread/Worker-Anzahl"
set xlabel "Threads/Worker"
set ylabel "Zeit (ms)"
set grid

plot "speed.dat" using 1:2 with linespoints title "1 Rechner (Ohne RMI)", \
     "" using 1:3 with linespoints title "1 Rechner (Mit RMI)", \
     "" using 1:4 with linespoints title "2 Rechner (Mit RMI)"