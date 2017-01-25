<?php

namespace AppBundle\Controller;

use AppBundle\Entity\Image;
use AppBundle\Entity\Legende;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Filesystem\Filesystem;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Annotation\Route;


class ImportController extends Controller
{
    /**
     * @param $name
     * @return \Symfony\Component\HttpFoundation\Response
     * @Route("/import-json")
     */
    public function indexAction()
    {
        $fichier = '../../ressources/dictionary.json';
        $fs = new Filesystem();
        if ($fs->exists($fichier)) {
            $content = file_get_contents($fichier);

            $contentJson = json_decode($content);

            if (! empty($contentJson)) {
                $em = $this->getDoctrine()->getEntityManager();
                $connection = $em->getConnection();
                $platform   = $connection->getDatabasePlatform();

                $connection->executeUpdate("SET foreign_key_checks = 0;");
                $connection->executeUpdate($platform->getTruncateTableSQL('couple_image', true ));
                $connection->executeUpdate($platform->getTruncateTableSQL('legende', true ));
                $connection->executeUpdate($platform->getTruncateTableSQL('image', true ));
                $connection->executeUpdate("SET foreign_key_checks = 1;");

                $compteur = 0;
                $miniCompteur = 0;

                foreach ($contentJson->pages as $page) {
                    $image = new Image();
                    $image->setNumero($page->page);

                    foreach ($page->legendes as $legende) {
                        $image->addLegende(
                            new Legende(
                                $legende->pos1->x,
                                $legende->pos1->y,
                                $legende->pos2->x,
                                $legende->pos2->y,
                                $legende->content
                            )
                        );
                    }
                    $image->setType(Image::$simple);
                    $em->persist($image);
                    $compteur++;
                    $miniCompteur++;
                    if ($miniCompteur === 200) {
                        $em->flush();
                        $em->clear();
                        $miniCompteur = 0;
                    }
                }
                $em->flush();
                return new JsonResponse(array('message' => "$compteur images enregistrées"));
            }
            return new JsonResponse(array('erreur' => 'Le json est vide.'));
        } else {
            return new JsonResponse(array('erreur' => 'Le fichier json est introuvable.'));
        }
    }
}