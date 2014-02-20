Druel damien

Doit contenir :
- ce que vous avez fait.
- ce que vous n'avez pas fait (et pourquoi).
- difficultés rencontrées.
- commentaires éventuels sur le TP (points à éclaircir, longueur du sujet, etc). 


----------------------------------------------------------------------------------------
- Tout fait sauf la question bonus ( tentative non concluante ) - voir snapshots pour chaque question
- pas de difficultés pour ce tp


Question 6 : Expliquez précisément le résultat obtenu

-> Dans un premier temps on dessine un triangle rouge prenant la partie supérieure du losange.
-> Ensuite on dessine un triangle vert prenant la partie gauche du losange
-> Puis on dessine un triangle bleu prenant la partie inférieure du losange
-> enfin on dessine un triangle turquoise prenant la partie droite du losange.

=> Le triangle rouge(partie sup) étant affiché avant le vert(partie gauche) et le turquoise(partie droite) 
Il est recouvert par les deux parties supérieurs (gauche et droite), du coup on ne le voit pas.

=> Le triangle vert(partie gauche) étant affiché avant le bleu(partie inf)
Il est recouvert par la partie gauche de ce dernier, donc on ne voit que la partie supérieure du triangle vert.

=> Le triangle vert(partie inf) étant affiché avant le turquoise(partie droite)
Il est recouvert par la partie inférieure de ce dernier, donc on ne voit que la partie gauche du triangle bleu.

=> Le triangle turquoise(partie gauche) étant affiché en dernier, on le voit en entier.


Question 7 : Testez et comprenez

-> Les triangles vert et turquoise sont ceux avec le depth le plus petit donc sont ceux qui sont affichés sur les autres


Question 8: Expliquez le résultat (écran blanc)

-> GL_GREATER définit que les points tracés seront ceux avec un depth au dessus du seuil.
Hors le seuil étant à 1 (10), et les points étants entre -20 et -10, aucun point n'est au dessus du seuil.


Question 12 :
1. _projection.setOrtho(-18,22,-10,30,5,100);
=> On deplace la caméra vers le haut et vers la droite donc on voit la forme en bas a gauche
2. _projection.setOrtho(-5,2,-10,10,5,100);
=> On déplace la caméra légèrement mais on rapproche et on change la taille des axes en diminuant l'ordonnée et 
en augmentant l'abscisse ce qui donne une forme disproportionnée.





