<?php
/**
 * This class implements Session On Memcached, On Windows
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

namespace Project\Library;

use Phalcon\Session\Adapter\AbstractAdapter;
use Phalcon\Cache\AdapterFactory;

/**
 * 从 Phalcon\Session\Adapter\Libmemcached 复制
 */
class SessionFactory extends AbstractAdapter
{
    /**
     * Constructor.
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
    public function __construct(AdapterFactory $factory, array $params = [])
    {
        $params['options']["prefix"] = "";
        $this->adapter     = $factory->newInstance($params['adapter'], $params['options']);
    }
}

?>