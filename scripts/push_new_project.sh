### We need at least 1 argument to use this script
  
if [[ $# < 2 ]] ; then
    echo "Please use this script as such : ./push_new_project.sh appName privateToken"
    exit 1
fi

## pic_starter_kit_android
## pHMtQFbhYYX17CYg3UtH
## Obennouna
## WNmJeFx1fKR6Yn5BTvjG

## Let's create the group on gitlab first
echo "Création du groupe : $1"
curl --header "PRIVATE-TOKEN: $2" -X POST --data "name=$1&path=$1&description=$1" "https://gitlab.rabat.sqli.com/api/v4/groups"

## Let's create the repository
echo "Création du repository : $1"
curl --header "PRIVATE-TOKEN: $2" -X POST "https://gitlab.rabat.sqli.com/api/v4/projects?name=$1&namespace_id=$1"

## Let's finally change the git remote to the new one
echo "Mise à jour de la configuration git"
git remote rename origin upstream
git remote add origin "git@gitlab.rabat.sqli.com:$1/$1.git"
git add .
git rm -rf scripts
git commit -m "Initial Commit"
git push origin master
git remote rm upstream
