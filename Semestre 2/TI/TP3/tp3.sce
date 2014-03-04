clear();
stacksize(100000000);
function im = quantification(img,n)
    quantificateur = 2^n
    im = img ./(256/quantificateur)
    im = im.*(256/quantificateur)
endfunction

function im = surEch(img,n)
  img = im2double(img);
  colonne = size(img,1)*n
  ligne = size(img,2)*n
  im = zeros(colonne,ligne)
  for i = 1 : colonne/n
      for j = 1 : ligne/n
          for n1 = 1 : n
              for n2 = 1 : n
                  x = i*n + n1-n
                  y = j*n + n2-n
                  im(x,y) = img(i,j);
              end
          end
      end
  end
endfunction

function imageSousEchantillonnee = sousEchantillonnage(image,n)
    image = im2double(image);
	
	colonne = size(image,1)/n
	ligne = size(image,2)/n
    
    imageSousEchantillonnee = zeros(colonne,ligne)
    for i = 1 : colonne
        for j = 1 : ligne
                for n1 = 1 : n
                    for n2 = 1 : n
                        x = i*n + n1-n
                        y = j*n + n2-n
       imageSousEchantillonnee(i,j) = imageSousEchantillonnee(i,j) + image(x,y);
                    end
                end
       imageSousEchantillonnee(i,j) = imageSousEchantillonnee(i,j) / (n*n);
        end
    end
endfunction

image = imread('image.png');
//imshow (image);

////////////    gris
grisRouge = image(:,:,1);
grisVert = image(:,:,2);
grisBleu = image(:,:,3);
lol = imshow([grisRouge, grisVert, grisBleu]);

////////////    couleurs
//imageRouge = image;
//imageRouge(:,:,2) = image(:,:,1)*0;
//imageRouge(:,:,3) = image(:,:,1)*0;
//imageBleu = image;
//imageBleu(:,:,1) = image(:,:,2)*0;
//imageBleu(:,:,3) = image(:,:,2)*0;
//imageVert = image;
//imageVert(:,:,1) = image(:,:,3)*0;
//imageVert(:,:,2) = image(:,:,3)*0;
//imshow(imageRouge + imageVert + imageBleu);

//////////// sous echantillonage


