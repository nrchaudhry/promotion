git pull

git add .
git commit -m "New Changes"
git push

mkdir ../promotion-deploys
cd ../promotion-deploys

git clone git@github.com:nrchaudhry/cwiztechpromotionapi.git

dirlist=$(find $1 -mindepth 1 -maxdepth 1 -type d)

for dir in $dirlist
do
  (
    cd $dir
    git pull

    rm -r src/main/java
    
    cp -r ../../promotion/src/main/java src/main/

    git add .
    git commit -m "New Changes"
    git push
  )
done

