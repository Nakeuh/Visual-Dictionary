var page = 0;
var dico, ratioX, ratioY;
var texte;
var bodyHeigth;
 
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
        img.setAttribute('heigth', bodyHeigth);
        img.setAttribute('width', document.body.clientWidth/2);
        emplacementDOM.appendChild(img);
       
        var zoomX = (document.body.clientWidth/2) / 2304;
        if (emplacement === 'gauche') {
            bodyHeigth = document.body.clientHeight;
        }
        var zoomY = (bodyHeigth+1.4) / 2776;
        ratioX = 4.16 * zoomX;
        ratioY = (emplacement === 'droite' ? 4.03 : 4.12) * zoomY;
       
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
                            getPos(tag.pos1.x, 'x', 0) + ',' + getPos(tag.pos1.y, 'y', 0) + ',' + getPos(tag.pos2.x, 'x', 10) + ',' + getPos(tag.pos2.y, 'y', 10)
                        );
                        //tagDom.setAttribute('href', 'https://www.google.fr/search?q=' + tag.content);
                        tagDom.setAttribute('alt', 'legend');
                        tagDom.setAttribute('data-content', tag.content);
                        

                        
                        $(tagDom).click(function(e) {
                    
                            var playListURL = 'http://en.wikipedia.org/w/api.php?format=json&action=query&titles=' + tag.content + '&rvprop=content&prop=extracts&callback=?';

                            
                            
                            $.getJSON(playListURL ,function(data) {
                                $.each(data.query.pages, function(i, item) {
                                    texte = item.extract;
                                });
                                
                                BootstrapDialog.show({
                                    title:tag.content,
                                    message: texte,
                                    buttons: [{
                                        label: 'Google Search',
                                        cssClass: 'btn-primary',
                                        action: function(){
                                            window.open("https://www.google.fr/search?q=" + tag.content);
                                        }
                                    }, {
                                        label: 'Google Images',
                                        cssClass: 'btn-primary',
                                        action: function(){
                                            window.open("https://www.google.fr/search?q=" + tag.content + "&tbm=isch");
                                        }
                                    }, {
                                        label: 'Wikipédia',
                                        cssClass: 'btn-primary',
                                        action: function(){
                                            window.open("https://en.wikipedia.org/wiki/" + tag.content);
                                        }
                                    }, {
                                        label: 'Fermer',
                                        cssClass: 'btn-warning',
                                        action: function(dialogItself){
                                            dialogItself.close();
                                        }
                                    }]
                                });   
                            });
                         });
                        
                        map.appendChild(tagDom);
                    });
                }
            }
        }
       
        /*$('img[usemap]').maphilight({
            alwaysOn: true
        });*/
       
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

function getPos(pos, dir, suppl) {
    return String(_.round(pos) * (dir === 'x' ? ratioX : (ratioY + (0.00004 * pos))) + suppl);
}