<?php

namespace AppBundle\Entity;

use ApiPlatform\Core\Annotation\ApiResource;
use Doctrine\ORM\Mapping as ORM;

/**
 * CoupleImage
 *
 * @ORM\Table(name="couple_image")
 * @ORM\Entity()
 * @ApiResource()
 */
class CoupleImage
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var Image
     * @ORM\OneToOne(targetEntity="AppBundle\Entity\Image", mappedBy="id")
     * @ORM\JoinColumn(name="gauche", referencedColumnName="id", nullable=false)
     *
     */
    private $gauche;

    /**
     * @var Image
     *
     * @ORM\OneToOne(targetEntity="AppBundle\Entity\Image", mappedBy="id")
     * @ORM\JoinColumn(name="droite", referencedColumnName="id", nullable=false)
     */
    private $droite;

    /**
     * @var Image
     * @ORM\OneToOne(targetEntity="AppBundle\Entity\CoupleImage", mappedBy="id")
     * @ORM\JoinColumn(name="suivant", referencedColumnName="id", nullable=false)
     *
     */
    private $suivant;

    /**
     * @var Image
     *
     * @ORM\OneToOne(targetEntity="AppBundle\Entity\CoupleImage", mappedBy="id")
     * @ORM\JoinColumn(name="precedent", referencedColumnName="id", nullable=false)
     */
    private $precendent;

    /**
     * @var Image
     *
     * @ORM\OneToOne(targetEntity="AppBundle\Entity\CoupleImage", mappedBy="id")
     * @ORM\JoinColumn(name="sommaire", referencedColumnName="id", nullable=false)
     */
    private $sommaire;


    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set gauche
     *
     * @param Image $gauche
     *
     * @return CoupleImage
     */
    public function setGauche($gauche)
    {
        $this->gauche = $gauche;

        return $this;
    }

    /**
     * Get gauche
     *
     * @return Image
     */
    public function getGauche()
    {
        return $this->gauche;
    }

    /**
     * Set droite
     *
     * @param Image $droite
     *
     * @return CoupleImage
     */
    public function setDroite($droite)
    {
        $this->droite = $droite;

        return $this;
    }

    /**
     * Get droite
     *
     * @return Image
     */
    public function getDroite()
    {
        return $this->droite;
    }

    /**
     * Set suivant
     *
     * @param \AppBundle\Entity\Image $suivant
     *
     * @return CoupleImage
     */
    public function setSuivant(\AppBundle\Entity\Image $suivant = null)
    {
        $this->suivant = $suivant;

        return $this;
    }

    /**
     * Get suivant
     *
     * @return \AppBundle\Entity\Image
     */
    public function getSuivant()
    {
        return $this->suivant;
    }

    /**
     * Set precendent
     *
     * @param \AppBundle\Entity\Image $precendent
     *
     * @return CoupleImage
     */
    public function setPrecendent(\AppBundle\Entity\Image $precendent = null)
    {
        $this->precendent = $precendent;

        return $this;
    }

    /**
     * Get precendent
     *
     * @return \AppBundle\Entity\Image
     */
    public function getPrecendent()
    {
        return $this->precendent;
    }

    /**
     * Set sommaire
     *
     * @param \AppBundle\Entity\Image $sommaire
     *
     * @return CoupleImage
     */
    public function setSommaire(\AppBundle\Entity\Image $sommaire = null)
    {
        $this->sommaire = $sommaire;

        return $this;
    }

    /**
     * Get sommaire
     *
     * @return \AppBundle\Entity\Image
     */
    public function getSommaire()
    {
        return $this->sommaire;
    }
}
