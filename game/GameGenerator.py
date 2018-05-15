

# Generates .config files randomly to effectively test the abilities of both player types.

import random as r
import sys

NUMPLAYERS = 10

#tuples containing the different attributes
hairLength = ('1', '2', '3', '4', '5', '6', '7', '8', '9', '10')
glasses = ('Harry-Potter', 'aviators', 'ray bans', 'circular', 'spectactles', 'shutter-shades', 'thick-rim', 'cat-eye', 'sport', 'shield')
facialHair = ('none', 'Hitler-stache', 'handlebars', 'goatie', 'Jesus-beard', 'fu-man-chu', 'sideburns', 'THE-CLEAN-SHAVEN-(wo)MAN', 'mutton-chops-(mm,-the-good-\'ol-mutton)', 'stubble')
eyeColor = ('brown', 'blue', 'blue-green', 'grey', 'hazel', 'red', 'green', 'black', 'purple', 'white')
pimples = ('you-got-those-really-like-nasty,-pus-filled-pimples', 'those-small-red-hard-ones-that-don\'t-pop', 'tiny-white-goo-dots', 'blackheads', 'OHH-the-ones-that-bleeed', 'like,-omg!', 'are-there-green-pimples?', 'the-pimples-you-get-when-they\'re-between-your-eyebrows-so-it-looks-like-you-have-a-unibrow', 'warts', 'boils,-because-you-know-you-got-to-boil-that-spaghetti.')
hat = ('fedora', 'outdoor', 'beanie', 'baseball', 'beret', 'tam-hat', '10-gallon', 'ski-helmet', 'climbing-helmet', 'those-beanies-with-a-pom-pom,-you-know-the-ones-on-the-back', 'I-used-to-have-a-rabbit-hat-with-ears-until-I-realized-it-was-an-embarrassment', 'panda-hat')
hairColor = ('blonde', 'strawberry-blonde', 'brown', 'black', 'dirty-blonde', 'red', 'ginger', 'silver', 'albino', 'platinum-blonde')
noseShape = ('long', 'slender', 'fat', 'round', 'sharp', 'bumpy', 'turned-up', 'combo', 'oblong', 'elephant')
faceShape = ('round', 'ovular', 'triangular', 'square', 'pentagonal', 'diamond', 'refined', 'sharp', 'chiseled', 'angular')

#dictionary of all attributes with attribute names for keys
attributes = {'hairLength':hairLength, 'glasses':glasses, 'facialHair':facialHair, 'eyeColor':eyeColor, 'pimples':pimples, 'hat':hat, 'hairColor':hairColor, 'noseShape':noseShape, 'faceShape':faceShape}

filename = input('enter file name: ')
configFile = open(filename + '.config', 'w')

for key, val in attributes.items():
    configFile.write(key + ' ')
    for trait in val:
        configFile.write(trait + ' ')
    configFile.write('\n')

for i in range(0,NUMPLAYERS):
    configFile.write('\n')
    configFile.write('P' + str(i+1) + '\n')
    for key, val in attributes.items():
        configFile.write(key + ' ' + val[r.randint(0,len(val) - 1)] + '\n')

chosenFile = open(filename + '.chosen', 'w')
chosenFile.write('P' + str(r.randint(1,NUMPLAYERS)) + ' ' + 'P' + str(r.randint(1,NUMPLAYERS)))
