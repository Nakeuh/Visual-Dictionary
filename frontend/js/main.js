var page = 0;
var dico;
var ratioX = 1.424, ratioY = 1.404;

$.get({url: "dictionary.json"}).then(function (data) {
    getPage(page);
    dico = data;
});

document.getElementById('fleche-droite').addEventListener('click', function(e) {
    e.preventDefault();

    if (page > 672) {
        page = 672;
        return;
    }
    
    page+= page === 0 ? 1 : 2;
    getPage();
});

document.getElementById('fleche-gauche').addEventListener('click', function(e) {
    e.preventDefault();
    
    if(page <= 0) {
        page = 0;
        return;
    }
    
    page -= 2;
    getPage();
});

document.getElementById('form-numero').addEventListener('submit', function(e) {
    e.preventDefault();
    page = Number(document.getElementById('input-numero').value);
    if (page) {
        page++;
    }
    if (page%2 === 0) {
        page--;
    }
    getPage();
});

function getPage() {
    ['gauche', 'droite'].forEach(function(emplacement) {
        var emplacementDOM = document.getElementById(emplacement);
        
        // On vide les éléments
        emplacementDOM.innerHTML = '';
        
        // On ajoute la nouvelle image
        var img = document.createElement("img");
        
        
        var pageName = '';
        
        if (page === 0) {
           if (emplacement === 'gauche') {
                pageName = 'first';
           } else {
               pageName = '0000';
           }
        } else {
            if (emplacement === 'gauche') {
                pageName = _.padStart(page, 4, '0');
            } else {
                pageName = _.padStart(page+1, 4, '0');
            }
        }
        
        img.setAttribute(
            "src",
            "images/Ultimate Visual Dictionary _" + pageName + ".jpg"
        );
        img.setAttribute(
            "usemap",
            "#" + emplacement + "map"
        );
        
        img.setAttribute("alt", emplacement);
        img.setAttribute("class", "page");
        img.setAttribute('id', 'img-' + emplacement);
        img.setAttribute('width', document.width/2);
        img.setAttribute('height', document.height);
        emplacementDOM.appendChild(img);
        
        // Création du map
        var map = document.createElement("map");
        map.setAttribute('name', emplacement + 'map');
        map.setAttribute('id', emplacement + 'map');
        
        if (! _.isUndefined(dico)) {
            var tags = _.filter(dico.pages, function(o) {
                return o.page === (emplacement === 'gauche' ? page : page +1)+1;
            });
            
            if (! _.isEmpty(tags)) {
                tags = tags[0];

                tags = _.filter(tags.tags, function(o) {
                    return o.tag === 'Legends';
                })

                if (! _.isEmpty(tags)) {
                    var tagDom = document.createElement('area');
                    tags = tags[0].values;
                    tags.forEach(function(tag) {
                        tagDom = document.createElement('area');
                        tagDom.setAttribute('shape', 'rect');
                        tagDom.setAttribute(
                            'coords',
                            String(_.round(tag.pos1.x)*ratioX) + ',' + String(_.round(tag.pos1.y)*ratioY) + ',' + String(_.round(tag.pos2.x)*ratioX) + ',' + String(_.round(tag.pos2.y)*ratioY)
                        );
                        tagDom.setAttribute('href', 'https://www.google.fr');
                        tagDom.setAttribute('alt', 'legend');
                        tagDom.setAttribute('data-content', tag.content);
                        map.appendChild(tagDom);
                    });
                }
            }
        }
        
        $('img[usemap]').maphilight({
            alwaysOn: true
        });
        
        emplacementDOM.appendChild(map);
        
        // Affichage ou non des flèches
        if (emplacement === 'gauche') {
            if (page === 0) {
                document.getElementById('fleche-gauche').style = 'display: none;';
            } else {
                document.getElementById('fleche-gauche').style = 'display: inline;';
            }
        } else if (emplacement === 'droite') {
            if (page === 672) {
                document.getElementById('fleche-droite').style = 'display: none;';
            } else {
                document.getElementById('fleche-droite').style = 'display: inline;';
            }
        }
    }); 
}