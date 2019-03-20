### We need at least 2 arguments to use this script

if [[ $# < 2 ]] ; then
    echo "Please use this script as such : ./create_new_project.sh appName packageName [fabric apiKey]"
    exit 1
fi

## Let's change the application name first
echo "Création de l'application : $1"
sed -i -- "s/Starter\ Kit\ Android/$1/g" app/src/main/res/values/strings.xml
#sed -i "s/\(<app_name.*>\)[^<>]*\(<\/app_name.*\)/\1$1\2/" app/src/main/res/values/strings.xml

## Let's change the package name now

# 1. Change build.gradle
echo "Changement du package name : $2"
echo "Application du nouveau nom de package dans le fichier build.gradle"
sed -i -- "s/com.sqli.starterkitandroid/$2/g" app/build.gradle

# 2. Change AndroidManifest.xml
echo "Application du nouveau nom de package dans le fichier AndroidManifest"
sed -i -- "s/com.sqli.starterkitandroid/$2/g" app/src/main/AndroidManifest.xml

# 3. Change folder names according to the package
echo "Création des dossiers du nouveau package name"
declare -a COMPONENTS=("main" "androidTest" "test")
ROOT="app/src/main/java/"
SOURCE="app/src/main/java/com/sqli/starterkitandroid/"

IN="$2"
packages=(${IN//./ })

RED='\033[0;31m'
NC='\033[0m' # No Color

for COMPONENT in "${COMPONENTS[@]}"
do
    ROOT_FOLDER=${ROOT//main/$COMPONENT}
    EXISTING_SOURCE=${SOURCE//main/$COMPONENT}
    NEW_ROOT_FOLDER=${ROOT_FOLDER}
    for folder in "${packages[@]}"
    do
	mkdir "${NEW_ROOT_FOLDER}$folder"
	NEW_ROOT_FOLDER="${NEW_ROOT_FOLDER}$folder/"
    done
    
# 4. Copy existing source file into new folders from package name
    echo "Copie des fichiers sources existants dans la nouvelle arborescence"
    cp -R ${EXISTING_SOURCE} ${NEW_ROOT_FOLDER}

# 5. Replace the package of every java file
    echo "Remplacement du package name dans les fichiers JAVA"
    find ${NEW_ROOT_FOLDER} -type f -print0 | xargs -0 sed -i -- "s/com.sqli.starterkitandroid/$2/g"
    echo "Suppression des fichiers sources depuis l'ancienne arborescence"
    rm -Rf ${EXISTING_SOURCE}
done

# 6. Delete old package folders
find . -type f -name '*--' -delete
echo "Suppression de l'ancienne arborescence"
find . -type d -empty -delete -mindepth 1

## [OPTIONAL] : Replace Crashlytics apiKey
if [[ ! -z "$3" ]] ; then
    echo "Remplacement de l'ApiKey Crashlytics par une autre"
    sed -i -- 's/^customFabricApiKey=*$/customFabricApiKey=${$4}/' .ignore
fi
