<?php

namespace AppBundle\Entity;

use ApiPlatform\Core\Annotation\ApiResource;
use Doctrine\ORM\Mapping as ORM;

/**
 * Legende
 *
 * @ORM\Table(name="legende")
 * @ORM\Entity()
 * @ApiResource()
 */
class Legende
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
     * @var float
     *
     * @ORM\Column(name="x1", type="float")
     */
    private $x1;

    /**
     * @var float
     *
     * @ORM\Column(name="y1", type="float")
     */
    private $y1;

    /**
     * @var float
     *
     * @ORM\Column(name="x2", type="float")
     */
    private $x2;

    /**
     * @var float
     *
     * @ORM\Column(name="y2", type="float")
     */
    private $y2;

    /**
     * @var string
     *
     * @ORM\Column(name="content", type="string", length=255)
     */
    private $content;

    /**
     * @var Legende
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\Image", inversedBy="legendes")
     * @ORM\JoinColumn(name="image", referencedColumnName="id", nullable=false)
     */
    private $image;

    /**
     * @var CoupleImage
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\CoupleImage")
     * @ORM\JoinColumn(name="lien", referencedColumnName="id", nullable=true)
     */
    private $lien = null;


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
     * Set x1
     *
     * @param float $x1
     *
     * @return Legende
     */
    public function setX1($x1)
    {
        $this->x1 = $x1;

        return $this;
    }

    /**
     * Get x1
     *
     * @return float
     */
    public function getX1()
    {
        return $this->x1;
    }

    /**
     * Set y1
     *
     * @param float $y1
     *
     * @return Legende
     */
    public function setY1($y1)
    {
        $this->y1 = $y1;

        return $this;
    }

    /**
     * Get y1
     *
     * @return float
     */
    public function getY1()
    {
        return $this->y1;
    }

    /**
     * Set x2
     *
     * @param float $x2
     *
     * @return Legende
     */
    public function setX2($x2)
    {
        $this->x2 = $x2;

        return $this;
    }

    /**
     * Get x2
     *
     * @return float
     */
    public function getX2()
    {
        return $this->x2;
    }

    /**
     * Set y2
     *
     * @param float $y2
     *
     * @return Legende
     */
    public function setY2($y2)
    {
        $this->y2 = $y2;

        return $this;
    }

    /**
     * Get y2
     *
     * @return float
     */
    public function getY2()
    {
        return $this->y2;
    }

    /**
     * Set content
     *
     * @param string $content
     *
     * @return Legende
     */
    public function setContent($content)
    {
        $this->content = $content;

        return $this;
    }

    /**
     * Get content
     *
     * @return string
     */
    public function getContent()
    {
        return $this->content;
    }

    /**
     * Set image
     *
     * @param \AppBundle\Entity\Image $image
     *
     * @return Legende
     */
    public function setImage(\AppBundle\Entity\Image $image)
    {
        $this->image = $image;

        return $this;
    }

    /**
     * Get image
     *
     * @return \AppBundle\Entity\Image
     */
    public function getImage()
    {
        return $this->image;
    }

    /**
     * Set lien
     *
     * @param \AppBundle\Entity\CoupleImage $lien
     *
     * @return Legende
     */
    public function setLien(\AppBundle\Entity\CoupleImage $lien = null)
    {
        $this->lien = $lien;

        return $this;
    }

    /**
     * Get lien
     *
     * @return \AppBundle\Entity\CoupleImage
     */
    public function getLien()
    {
        return $this->lien;
    }
}
