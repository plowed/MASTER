//TI : TP3 : sur et sous-échantillonnage
//binome : Ryutoor et Allart

function sous = TISousEchantillonnage(image,n)
    sous = zeros(size(DonneeRougeGris,1)/n,size(DonneeRougeGris,2)/n);
    couleurValeur = 0;
//    i = 1;
//    j = 1;
    
    for i = 1:n : size(image,1)
        for j = 1:n: size(image,2)
            for k =0:n-1
                for l=0:n-1
                    couleurValeur = couleurValeur + image(i+k,j+l);
                end
            end
            couleurValeur = couleurValeur/(n*n);
            sous(i,j)=couleurValeur;
            couleurValeur = 0;
        end
    end
//    while (i<size(image,2))
//        while (j<size(image,1))
//            for k =0:n-1
//                for l=0:n-1
//                    couleurValeur = couleurValeur + image(i+k,j+l);
//                end
//            end
//            couleurValeur = couleurValeur/(n*n);
//        end
//        sous(i,j) = couleurValeur;
//        j = j+n;
//        i = i+n;
//        couleurValeur = 0;
//    end
endfunction
