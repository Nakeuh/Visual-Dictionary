<?php

namespace AppBundle\Entity;

use ApiPlatform\Core\Annotation\ApiResource;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\ORM\Mapping as ORM;

/**
 * Image
 *
 * @ORM\Table(name="image")
 * @ORM\Entity()
 * @ApiResource()
 */
class Image
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
     * @var int
     *
     * @ORM\Column(name="numero", type="integer", length=255)
     */
    private $numero;

    static $simple = 1;
    static $sommaire = 2;

    /**
     * @var int
     *
     * @ORM\Column(name="type", type="smallint")
     */
    private $type = 1;

    /**
     * @var ArrayCollection
     *
     * @ORM\OneToMany(targetEntity="AppBundle\Entity\Legende", mappedBy="id", cascade={"all"})
     */
    private $legendes;

    /**
     * Image constructor.
     */
    public function __construct()
    {
        $this->legendes = new ArrayCollection();
    }


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
     * Set numero
     *
     * @param int $numero
     *
     * @return Image
     */
    public function setNumero($numero)
    {
        $this->numero = $numero;

        return $this;
    }

    /**
     * Get numero
     *
     * @return int
     */
    public function getNumero()
    {
        return $this->numero;
    }

    /**
     * Set type
     *
     * @param integer $type
     *
     * @return Image
     */
    public function setType($type)
    {
        $this->type = $type;

        return $this;
    }

    /**
     * Get type
     *
     * @return int
     */
    public function getType()
    {
        return $this->type;
    }

    /**
     * Set legendes
     *
     * @param ArrayCollection $legendes
     *
     * @return Image
     */
    public function setLegendes($legendes)
    {
        $this->legendes = $legendes;

        return $this;
    }

    /**
     * Get legendes
     *
     * @return ArrayCollection
     */
    public function getLegendes()
    {
        return $this->legendes;
    }

    /**
     * Add legende
     *
     * @param \AppBundle\Entity\Legende $legende
     *
     * @return Image
     */
    public function addLegende(\AppBundle\Entity\Legende $legende)
    {
        $legende->setImage($this);
        $this->legendes->add($legende);

        return $this;
    }

    /**
     * Remove legende
     *
     * @param \AppBundle\Entity\Legende $legende
     */
    public function removeLegende(\AppBundle\Entity\Legende $legende)
    {
        $this->legendes->removeElement($legende);
    }
}
