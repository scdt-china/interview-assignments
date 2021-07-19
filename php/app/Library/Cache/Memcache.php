<?php
/**
 * This class implements Memcached On Windows
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

namespace Project\Library\Cache;

use Phalcon\Storage\Adapter\AbstractAdapter;
use Phalcon\Storage\SerializerFactory;
use Phalcon\Storage\Exception;

class Memcache extends AbstractAdapter
{
    /**
     * @var array
     */
    protected $options = [];

    /**
     * Memcache constructor.
     *
     * @param array options = [
     *     'servers' => [
     *         [
     *             'host'       => '127.0.0.1',
     *             'port'       => 11211,
     *             'persistent' => true,
     *             'weight'     => 1
     *         ]
     *     ],
     *     'defaultSerializer' => 'Php',
     *     'lifetime' => 3600,
     *     'serializer' => null,
     *     'prefix' => ''
     * ]
     */
    public function __construct(SerializerFactory $factory, array $options = [])
    {
        if (!isset($options["servers"]))
        {
            $options["servers"] = [
                [
                    'host'       => "127.0.0.1",
                    'port'       => 11211,
                    'persistent' => true,
                    'weight'     => 1
                ]
            ];
        }

        $this->prefix  = "ph-memc-";
        $this->options = $options;

        parent::__construct($factory, $options);

        $this->initSerializer();
    }

    /**
     * Flushes/clears the cache
     *
     * @return bool
     * @throws Exception
     */
    public function clear() : bool
    {
        return $this->getAdapter()->flush();
    }

    /**
     * Decrements a stored number
     */
    public function decrement(string $key, int $value = 1)
    {
        return $this->getAdapter()->decrement($this->getPrefixedKey($key), $value);
    }

    /**
     * Remove data from the adapter
     *
     * @param string $key
     *
     * @return bool
     * @throws Exception
     */
    public function delete(string $key) : bool
    {
        return $this->getAdapter()->delete($this->getPrefixedKey($key), 0);
    }

    /**
     * Reads data from the adapter
     *
     * @param string $key
     * @param null   $defaultValue
     *
     * @return mixed
     * @throws Exception
     */
    public function get(string $key, $defaultValue = null)
    {
        return $this->getUnserializedData($this->getAdapter()->get($this->getPrefixedKey($key)), $defaultValue);
    }

    /**
     * Returns the already connected adapter or connects to the Memcached
     * server(s)
     *
     * @return \Memcached
     * @throws Exception
     */
    public function getAdapter()
    {
        if (null === $this->adapter) {
            $this->adapter = new \Memcache();
            foreach ($this->options['servers'] as $server) {
                $this->adapter->addServer(
                    $server['host'],
                    $server['port'],
                    isset($server['persistent']) ? $server['persistent'] : true,
                    isset($server['weight']) ? $server['weight'] : 100
                );
            }
        }
        return $this->adapter;
    }

    /**
     * Stores data in the adapter
     *
     * @return array
     */
    public function getKeys(string $prefix = "") : array
    {
        return [];
    }

    /**
     * Checks if an element exists in the cache
     *
     * @param string $key
     *
     * @return bool
     * @throws Exception
     */
    public function has(string $key) : bool
    {
        return !(false === $this->getAdapter()->get($this->getPrefixedKey($key)));
    }

    /**
     * Increments a stored number
     *
     * @param string $key
     * @param int    $value
     *
     * @return bool|int
     * @throws Exception
     */
    public function increment(string $key, int $value = 1)
    {
        return $this->getAdapter()->increment($this->getPrefixedKey($key), $value);
    }

    /**
     * Stores data in the adapter
     *
     * @param string $key
     * @param mixed  $value
     * @param null   $ttl
     *
     * @return bool
     * @throws Exception
     */
    public function set(string $key, $value, $ttl = null) : bool
    {
        return $this->getAdapter()->set($this->getPrefixedKey($key), $this->getSerializedData($value), 0, $this->getTtl($ttl));
    }
}

?>