# -----------------------------------------------------------------------
# Extraction d'attributs de pixels pour la classification,
# Module RdF, reconnaissance de formes
# Copyleft (C) 2014, Universite Lille 1
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
# -----------------------------------------------------------------------

# Chargement des fonctions externes
library ("EBImage")
source ("rdfSegmentation.R")


#### PARTIE 1 ####
# Chargement d'une image
#nom <- "rdf-2-classes-texture-1.png"
#image <- rdfReadGreyImage (nom)

# Calcul et affichage de son histogramme
#nbins <- 256
#h <- hist (as.vector (image), breaks = seq (0, 1, 1 / nbins))



# Segmentation par binarisation
#seuil <- 0.58
#binaire <- (image - seuil) >= 0

# Affichage des deux images
#if (interactive ()) {
  #display (image, nom)
  #display (binaire, "image binaire")
#}

######PARTIE 2#######


#nom <- "rdf-2-classes-texture-4.png"
#image <- rdfReadGreyImage (nom)
#seuil <- 0.45

#binaire <- (    seuil - image  )>= 0##
#binaire <- (   image - seuil   )>= 0##

#display (binaire, "image binaire")

#nom2 <- "rdf-masque-ronds.png"
#image2 <- rdfReadGreyImage (nom2)
#a <- abs(binaire-image2)
#b <- sum(a)
#pourc <- (b / 16384)*100

#display (a, "comparaison")


#### Partie 3 ####

#nom <- "rdf-2-classes-texture-4.png"
#image <- rdfReadGreyImage (nom)

#image2 <- rdfTextureEcartType (image, 2)

# afficher image niv texture
#display (image, "image originale")


#display (image2, "image de niveau de texture")

# Calcul et affichage de son histogramme
#nbins <- 256
#h <- hist (as.vector (image2), breaks = seq (0, 1, 1 / nbins))


#seuil <- 0.34

#binaire <- (    seuil - image2  )>= 0##

#binaire <- (   image2 - seuil   )>= 0##
#display (binaire, "image binaire")

#nom2 <- "rdf-masque-ronds.png"
#image3 <- rdfReadGreyImage (nom2)
#a <- abs(binaire-image3)
#b <- sum(a)
#pourc <- (b / 16384)*100

#display (a, "comparaison")



##### PARTIE 4 #######

nom <- "rdf-2-classes-texture-4.png"
image <- rdfReadGreyImage (nom)

image2 <- rdfTextureEcartType (image, 2)

# afficher image niv texture
display (image, "image originale")
display (image2, "image de niveau de texture")



# Calcul et affichage de son histogramme
nbins <- 256
histo <- rdfCalculeHistogramme2D(image, nbins, image2, nbins)
#h <- hist (histo, breaks = seq (0, 1, 1 / nbins))

display(histo, "histogramme 2D")


