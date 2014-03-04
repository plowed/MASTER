//TI : TP3
//binome : Ruytoor Allart

//chargement de fichier 
//exec ("TIEchantillonnage.sci", -1);
// charge l'image
nom = "image.png";
image = imread(nom);
//dimension de la variable utilisé est de type "hypermat" : matrice de N dimension


//séparation des composantes rouge,verte,bleue en 3 images en niveaux de gris
//rouge
DonneeRougeGris = image;
DonneeRougeGris(:,:,2) = image(:,:,1)*0;
DonneeRougeGris(:,:,3) = image(:,:,1)*0;

//imshow(DonneeRougeGris);
ImageRougeGris = imshow(DonneeRougeGris);
////verte
DonneeVertGris = image;
DonneeVertGris(:,:,1) = image(:,:,1)*0;
DonneeVertGris(:,:,3) = image(:,:,3)*0;

mageVertGris = imshow(DonneeVertGris);

////bleue
DonneeBlueGris = image;
DonneeBlueGris(:,:,2) = image(:,:,2)*0;
DonneeBlueGris(:,:,1) = image(:,:,1)*0;
ImageBlueGris = imshow(DonneeBlueGris, []);

//
////affichage des composantes couleurs en rouge, verte et bleue
//imshow([DonneeRougeGris,DonneeVertGris,DonneeBlueGris]);
//
//affichage de l'image
//imshow(image);

//résolution
//pouce = 2.54
//res = 72;
//dimension_support = [size(image,1)/res*pouce,size(image,2)/res*pouce];

