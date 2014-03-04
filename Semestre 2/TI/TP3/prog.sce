
function imgr=Reduc (image)
    imgr=image(1:size(image,1)/2,1:size(image,2)/2,:);
    imgr(1,1,1)=image(1,1,1)/4+image(2,1,1)/4+image(1,2,1)/4+image(2,2,1)/4;
    imgr(1,1,2)=image(1,1,2)/4+image(2,1,2)/4+image(1,2,2)/4+image(2,2,2)/4;
    imgr(1,1,3)=image(1,1,3)/4+image(2,1,3)/4+image(1,2,3)/4+image(2,2,3)/4;
    for i=2:size(imgr,1)-2
        printf('i : %d\n',i)
        for j=2:size(imgr,2)-1
            imgr(i,j,1)=image(i*2,j*2,1)/4+image((i*2)+1,j*2,1)/4+image(i*2,(j*2)+1,1)/4+image((i*2)+1,(j*2)+1,1)/4;
            imgr(i,j,2)=image(i*2,j*2,2)/4+image((i*2)+1,j*2,2)/4+image(i*2,(j*2)+1,2)/4+image((i*2)+1,(j*2)+1,2)/4;
            imgr(i,j,3)=image(i*2,j*2,3)/4+image((i*2)+1,j*2,3)/4+image(i*2,(j*2)+1,3)/4+image((i*2)+1,(j*2)+1,3)/4;
        end// 1*2 = 2 on loupe l'indice 1
        imgr(i,1,1)=image(i*2,1,1)/4+image((i*2)+1,1,1)/4+image(i*2,2,1)/4+image((i*2)+1,2,1)/4;
        imgr(i,1,2)=image(i*2,1,2)/4+image((i*2)+1,1,2)/4+image(i*2,2,2)/4+image((i*2)+1,2,2)/4;
        imgr(i,1,3)=image(i*2,1,3)/4+image((i*2)+1,1,3)/4+image(i*2,2,3)/4+image((i*2)+1,2,3)/4;
    end
    for j=2:size(imgr,2)-1
        imgr(1,j,1)=image(1,j*2,1)/4+image(2,j*2,1)/4+image(1,(j*2)+1,1)/4+image(2,(j*2)+1,1)/4;
        imgr(1,j,2)=image(1,j*2,2)/4+image(2,j*2,2)/4+image(1,(j*2)+1,2)/4+image(2,(j*2)+1,2)/4;
        imgr(1,j,3)=image(1,j*2,3)/4+image(2,j*2,3)/4+image(1,(j*2)+1,3)/4+image(2,(j*2)+1,3)/4;
    end
endfunction


function imgr=ReducBL (image)
    imgr=image(1:size(image,1)/2,1:size(image,2)/2);
    imgr(1,1)=image(1,1)/4+image(2,1)/4+image(1,2)/4+image(2,2)/4;
    for i=2:size(imgr,1)-1
        printf('i : %d\n',i)
        for j=2:size(imgr,2)-1
            //printf('j : %d\n',j)
            imgr(i,j)=image(i*2,j*2)/4+image((i*2)+1,j*2)/4+image(i*2,(j*2)+1)/4+image((i*2)+1,(j*2)+1)/4;
        end// 1*2 = 2 on loupe l'indice 1
        imgr(i,1)=image(i*2,1)/4+image((i*2)+1,1)/4+image(i*2,2)/4+image((i*2)+1,2)/4;
    end
    for j=2:size(imgr,2)-1
        imgr(1,j)=image(1,j*2)/4+image(2,j*2)/4+image(1,(j*2)+1)/4+image(2,(j*2)+1)/4;
    end
endfunction

function imgr = sousEch(image, n)
    imgr=image(1:size(image,1)/2,1:size(image,2)/2,:);
    imgr(1,1,n)=image(1,1,n)/4+image(2,1,n)/4+image(1,2,n)/4+image(2,2,n)/4;
    for i=2:size(imgr,1)-2
        printf('i : %d\n',i)
        for j=2:size(imgr,2)-1
            imgr(i,j,n)=image(i*2,j*2,n)/4+image((i*2)+1,j*2,n)/4+image(i*2,(j*2)+1,n)/4+image((i*2)+1,(j*2)+1,n)/4;
        end// 1*2 = 2 on loupe l'indice 1
        imgr(i,1,n)=image(i*2,1,n)/4+image((i*2)+1,1,n)/4+image(i*2,2,n)/4+image((i*2)+1,2,n)/4;
    end
    for j=2:size(imgr,2)-1
        imgr(1,j,n)=image(1,j*2,n)/4+image(2,j*2,n)/4+image(1,(j*2)+1,n)/4+image(2,(j*2)+1,n)/4;
    end
endfunction

function imgr = surEch(image, n)
    imgr = [image(:,:,:),image(:,:,:);image(:,:,:),image(:,:,:)];
    imgr(1,1,n)=image(1,1,n);
    for i=2:size(imgr,1)
        printf('i : %d\n',i)
        for j=2:size(imgr,2)
            imgr(i,j,n)=image(i/2,j/2,n);
        end// 1*2 = 2 on loupe l'indice 1
    end
endfunction

//Question 2
//n = 2^8 / 2^n bit
function img= Quantif (image,ninitial,n)
    quantificateur=2^(ninitial-n);
    img=image;
    img=image./(quantificateur);
    round(img);
    img=img.*(quantificateur);
endfunction

function n = motif (image)
    n = 0;
    for j=1:size(image,2)
        if(image(50,j)==255)
            n = n+1;
        end
        
    end
endfunction


//im=imread("ti-semaine-3-lena.png");
//im=imread("ti-semaine-3-mire.png");
im = imread("ti-semaine-3-sinus.png");
stacksize(268435454);

//im = Quantif(im,8,1);
//red=im;
//green=im;
//blue=im;
//
//red(:,:,2)=im(:,:,2)*0;
//red(:,:,3)=im(:,:,3)*0;
//green(:,:,3)=im(:,:,3)*0;
//green(:,:,1)=im(:,:,1)*0;
//greenG=green(:,:,2);
//blue(:,:,1)=im(:,:,1)*0;
//blue(:,:,2)=im(:,:,2)*0;
//blueG=blue(:,:,3);
//imshow ([redG,greenG,blueG]);

tailleP=size(red);
tailleP=tailleP/72;//pouce
tailleP=tailleP*2.54;
//imr=Reduc(im);
//imshow(red);
//imr = sousEch(green,2);
//imr2 = surEch(imr,2);
//imshow([green,imr2]);

//greenn = Quantif(im,8,3);
//green1=Quantif(im,1);
//green2=Quantif(im,2);
//green3=Quantif(im,3);
//green4=Quantif(im,4);
//green5=Quantif(im,5);
//green2G=green2(:,:,2);
//imrRed=Reduc(red)
//imshow([im,green1,green2,green3,green4,green5])
imshow(im);
n = motif(im)/3;
nbmotif = size(im,2)/n;
