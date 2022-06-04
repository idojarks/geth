package com.example.geth;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Contracts_Dragon721_sol_Dragon721 extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b50604080518082018252600980825268447261676f6e37323160b81b6020808401828152855180870190965292855284015281519192916200005691600091620000e5565b5080516200006c906001906020840190620000e5565b50505062000089620000836200008f60201b60201c565b62000093565b620001c8565b3390565b600780546001600160a01b038381166001600160a01b0319831681179093556040519116919082907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a35050565b828054620000f3906200018b565b90600052602060002090601f01602090048101928262000117576000855562000162565b82601f106200013257805160ff191683800117855562000162565b8280016001018555821562000162579182015b828111156200016257825182559160200191906001019062000145565b506200017092915062000174565b5090565b5b8082111562000170576000815560010162000175565b600181811c90821680620001a057607f821691505b60208210811415620001c257634e487b7160e01b600052602260045260246000fd5b50919050565b61212880620001d86000396000f3fe608060405234801561001057600080fd5b506004361061012c5760003560e01c8063715018a6116100ad578063a22cb46511610071578063a22cb46514610278578063b88d4fde1461028b578063c87b56dd1461029e578063e985e9c5146102b1578063f2fde38b146102ed57600080fd5b8063715018a61461023b5780637299a729146102435780638da5cb5b1461024a57806395d89b411461025b5780639c0cc30c1461026357600080fd5b806323b872dd116100f457806323b872dd146101ce5780632aedc3f9146101e157806342842e0e146102025780636352211e1461021557806370a082311461022857600080fd5b806301ffc9a71461013157806306fdde0314610159578063081812fc1461016e578063095ea7b314610199578063167ddf6e146101ae575b600080fd5b61014461013f3660046119d2565b610300565b60405190151581526020015b60405180910390f35b610161610352565b6040516101509190611a47565b61018161017c366004611a5a565b6103e4565b6040516001600160a01b039091168152602001610150565b6101ac6101a7366004611a8f565b610471565b005b6101c16101bc366004611a5a565b610587565b6040516101509190611b0a565b6101ac6101dc366004611b1d565b61084b565b6101f46101ef366004611c05565b61087c565b604051908152602001610150565b6101ac610210366004611b1d565b61095b565b610181610223366004611a5a565b610976565b6101f4610236366004611c9e565b6109ed565b6101ac610a74565b6000610181565b6007546001600160a01b0316610181565b610161610aaa565b61026b610ab9565b6040516101509190611cb9565b6101ac610286366004611d1b565b610d5b565b6101ac610299366004611d57565b610d6a565b6101616102ac366004611a5a565b610da2565b6101446102bf366004611dd3565b6001600160a01b03918216600090815260056020908152604080832093909416825291909152205460ff1690565b6101ac6102fb366004611c9e565b610f2e565b60006001600160e01b031982166380ac58cd60e01b148061033157506001600160e01b03198216635b5e139f60e01b145b8061034c57506301ffc9a760e01b6001600160e01b03198316145b92915050565b60606000805461036190611e06565b80601f016020809104026020016040519081016040528092919081815260200182805461038d90611e06565b80156103da5780601f106103af576101008083540402835291602001916103da565b820191906000526020600020905b8154815290600101906020018083116103bd57829003601f168201915b5050505050905090565b60006103ef82610fc9565b6104555760405162461bcd60e51b815260206004820152602c60248201527f4552433732313a20617070726f76656420717565727920666f72206e6f6e657860448201526b34b9ba32b73a103a37b5b2b760a11b60648201526084015b60405180910390fd5b506000908152600460205260409020546001600160a01b031690565b600061047c82610976565b9050806001600160a01b0316836001600160a01b031614156104ea5760405162461bcd60e51b815260206004820152602160248201527f4552433732313a20617070726f76616c20746f2063757272656e74206f776e656044820152603960f91b606482015260840161044c565b336001600160a01b0382161480610506575061050681336102bf565b6105785760405162461bcd60e51b815260206004820152603860248201527f4552433732313a20617070726f76652063616c6c6572206973206e6f74206f7760448201527f6e6572206e6f7220617070726f76656420666f7220616c6c0000000000000000606482015260840161044c565b6105828383610fe6565b505050565b6105ab60405180606001604052806060815260200160608152602001606081525090565b60008281526009602090815260409182902091517fc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470926105ed92909101611e41565b6040516020818303038152906040528051906020012014156106475760405162461bcd60e51b81526020600482015260136024820152726e6f6e6578697374656e7420617274776f726b60681b604482015260640161044c565b61065082610fc9565b6106765760405162461bcd60e51b8152602060048201526000602482015260440161044c565b6000828152600960205260409081902081516060810190925280548290829061069e90611e06565b80601f01602080910402602001604051908101604052809291908181526020018280546106ca90611e06565b80156107175780601f106106ec57610100808354040283529160200191610717565b820191906000526020600020905b8154815290600101906020018083116106fa57829003601f168201915b5050505050815260200160018201805461073090611e06565b80601f016020809104026020016040519081016040528092919081815260200182805461075c90611e06565b80156107a95780601f1061077e576101008083540402835291602001916107a9565b820191906000526020600020905b81548152906001019060200180831161078c57829003601f168201915b505050505081526020016002820180546107c290611e06565b80601f01602080910402602001604051908101604052809291908181526020018280546107ee90611e06565b801561083b5780601f106108105761010080835404028352916020019161083b565b820191906000526020600020905b81548152906001019060200180831161081e57829003601f168201915b5050505050815250509050919050565b6108553382611054565b6108715760405162461bcd60e51b815260040161044c90611edd565b61058283838361113a565b6007546000906001600160a01b031633146108a95760405162461bcd60e51b815260040161044c90611f2e565b6108b7600880546001019055565b60006108c260085490565b604080516060810182528881526020808201899052818301889052600084815260098252929092208151805194955091939092610903928492910190611923565b50602082810151805161091c9260018501920190611923565b5060408201518051610938916002840191602090910190611923565b5090505061094683826112d6565b61095081856112f0565b90505b949350505050565b61058283838360405180602001604052806000815250610d6a565b6000818152600260205260408120546001600160a01b03168061034c5760405162461bcd60e51b815260206004820152602960248201527f4552433732313a206f776e657220717565727920666f72206e6f6e657869737460448201526832b73a103a37b5b2b760b91b606482015260840161044c565b60006001600160a01b038216610a585760405162461bcd60e51b815260206004820152602a60248201527f4552433732313a2062616c616e636520717565727920666f7220746865207a65604482015269726f206164647265737360b01b606482015260840161044c565b506001600160a01b031660009081526003602052604090205490565b6007546001600160a01b03163314610a9e5760405162461bcd60e51b815260040161044c90611f2e565b610aa8600061137b565b565b60606001805461036190611e06565b60606000610ac660085490565b905060008167ffffffffffffffff811115610ae357610ae3611b59565b604051908082528060200260200182016040528015610b3857816020015b610b2560405180606001604052806060815260200160608152602001606081525090565b815260200190600190039081610b015790505b50905060005b82811015610d545760096000610b55836001611f79565b8152602001908152602001600020604051806060016040529081600082018054610b7e90611e06565b80601f0160208091040260200160405190810160405280929190818152602001828054610baa90611e06565b8015610bf75780601f10610bcc57610100808354040283529160200191610bf7565b820191906000526020600020905b815481529060010190602001808311610bda57829003601f168201915b50505050508152602001600182018054610c1090611e06565b80601f0160208091040260200160405190810160405280929190818152602001828054610c3c90611e06565b8015610c895780601f10610c5e57610100808354040283529160200191610c89565b820191906000526020600020905b815481529060010190602001808311610c6c57829003601f168201915b50505050508152602001600282018054610ca290611e06565b80601f0160208091040260200160405190810160405280929190818152602001828054610cce90611e06565b8015610d1b5780601f10610cf057610100808354040283529160200191610d1b565b820191906000526020600020905b815481529060010190602001808311610cfe57829003601f168201915b505050505081525050828281518110610d3657610d36611f91565b60200260200101819052508080610d4c90611fa7565b915050610b3e565b5092915050565b610d663383836113cd565b5050565b610d743383611054565b610d905760405162461bcd60e51b815260040161044c90611edd565b610d9c8484848461149c565b50505050565b6060610dad82610fc9565b610e135760405162461bcd60e51b815260206004820152603160248201527f45524337323155524953746f726167653a2055524920717565727920666f72206044820152703737b732bc34b9ba32b73a103a37b5b2b760791b606482015260840161044c565b60008281526006602052604081208054610e2c90611e06565b80601f0160208091040260200160405190810160405280929190818152602001828054610e5890611e06565b8015610ea55780601f10610e7a57610100808354040283529160200191610ea5565b820191906000526020600020905b815481529060010190602001808311610e8857829003601f168201915b505050505090506000610ee060408051808201909152601581527468747470733a2f2f697066732e696f2f697066732f60581b602082015290565b9050805160001415610ef3575092915050565b815115610f25578082604051602001610f0d929190611fc2565b60405160208183030381529060405292505050919050565b610953846114cf565b6007546001600160a01b03163314610f585760405162461bcd60e51b815260040161044c90611f2e565b6001600160a01b038116610fbd5760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b606482015260840161044c565b610fc68161137b565b50565b6000908152600260205260409020546001600160a01b0316151590565b600081815260046020526040902080546001600160a01b0319166001600160a01b038416908117909155819061101b82610976565b6001600160a01b03167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92560405160405180910390a45050565b600061105f82610fc9565b6110c05760405162461bcd60e51b815260206004820152602c60248201527f4552433732313a206f70657261746f7220717565727920666f72206e6f6e657860448201526b34b9ba32b73a103a37b5b2b760a11b606482015260840161044c565b60006110cb83610976565b9050806001600160a01b0316846001600160a01b031614806111065750836001600160a01b03166110fb846103e4565b6001600160a01b0316145b8061095357506001600160a01b0380821660009081526005602090815260408083209388168352929052205460ff16610953565b826001600160a01b031661114d82610976565b6001600160a01b0316146111b15760405162461bcd60e51b815260206004820152602560248201527f4552433732313a207472616e736665722066726f6d20696e636f72726563742060448201526437bbb732b960d91b606482015260840161044c565b6001600160a01b0382166112135760405162461bcd60e51b8152602060048201526024808201527f4552433732313a207472616e7366657220746f20746865207a65726f206164646044820152637265737360e01b606482015260840161044c565b61121e600082610fe6565b6001600160a01b0383166000908152600360205260408120805460019290611247908490611ff1565b90915550506001600160a01b0382166000908152600360205260408120805460019290611275908490611f79565b909155505060008181526002602052604080822080546001600160a01b0319166001600160a01b0386811691821790925591518493918716917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef91a4505050565b610d668282604051806020016040528060008152506115c4565b6112f982610fc9565b61135c5760405162461bcd60e51b815260206004820152602e60248201527f45524337323155524953746f726167653a2055524920736574206f66206e6f6e60448201526d32bc34b9ba32b73a103a37b5b2b760911b606482015260840161044c565b6000828152600660209081526040909120825161058292840190611923565b600780546001600160a01b038381166001600160a01b0319831681179093556040519116919082907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a35050565b816001600160a01b0316836001600160a01b0316141561142f5760405162461bcd60e51b815260206004820152601960248201527f4552433732313a20617070726f766520746f2063616c6c657200000000000000604482015260640161044c565b6001600160a01b03838116600081815260056020908152604080832094871680845294825291829020805460ff191686151590811790915591519182527f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31910160405180910390a3505050565b6114a784848461113a565b6114b3848484846115f7565b610d9c5760405162461bcd60e51b815260040161044c90612008565b60606114da82610fc9565b61153e5760405162461bcd60e51b815260206004820152602f60248201527f4552433732314d657461646174613a2055524920717565727920666f72206e6f60448201526e3732bc34b9ba32b73a103a37b5b2b760891b606482015260840161044c565b600061157260408051808201909152601581527468747470733a2f2f697066732e696f2f697066732f60581b602082015290565b9050600081511161159257604051806020016040528060008152506115bd565b8061159c846116f2565b6040516020016115ad929190611fc2565b6040516020818303038152906040525b9392505050565b6115ce83836117f0565b6115db60008484846115f7565b6105825760405162461bcd60e51b815260040161044c90612008565b60006001600160a01b0384163b156116ea57604051630a85bd0160e11b81526001600160a01b0385169063150b7a029061163b90339089908890889060040161205a565b6020604051808303816000875af1925050508015611676575060408051601f3d908101601f1916820190925261167391810190612097565b60015b6116d0573d8080156116a4576040519150601f19603f3d011682016040523d82523d6000602084013e6116a9565b606091505b5080516116c85760405162461bcd60e51b815260040161044c90612008565b805181602001fd5b6001600160e01b031916630a85bd0160e11b149050610953565b506001610953565b6060816117165750506040805180820190915260018152600360fc1b602082015290565b8160005b8115611740578061172a81611fa7565b91506117399050600a836120ca565b915061171a565b60008167ffffffffffffffff81111561175b5761175b611b59565b6040519080825280601f01601f191660200182016040528015611785576020820181803683370190505b5090505b84156109535761179a600183611ff1565b91506117a7600a866120de565b6117b2906030611f79565b60f81b8183815181106117c7576117c7611f91565b60200101906001600160f81b031916908160001a9053506117e9600a866120ca565b9450611789565b6001600160a01b0382166118465760405162461bcd60e51b815260206004820181905260248201527f4552433732313a206d696e7420746f20746865207a65726f2061646472657373604482015260640161044c565b61184f81610fc9565b1561189c5760405162461bcd60e51b815260206004820152601c60248201527f4552433732313a20746f6b656e20616c7265616479206d696e74656400000000604482015260640161044c565b6001600160a01b03821660009081526003602052604081208054600192906118c5908490611f79565b909155505060008181526002602052604080822080546001600160a01b0319166001600160a01b03861690811790915590518392907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef908290a45050565b82805461192f90611e06565b90600052602060002090601f0160209004810192826119515760008555611997565b82601f1061196a57805160ff1916838001178555611997565b82800160010185558215611997579182015b8281111561199757825182559160200191906001019061197c565b506119a39291506119a7565b5090565b5b808211156119a357600081556001016119a8565b6001600160e01b031981168114610fc657600080fd5b6000602082840312156119e457600080fd5b81356115bd816119bc565b60005b83811015611a0a5781810151838201526020016119f2565b83811115610d9c5750506000910152565b60008151808452611a338160208601602086016119ef565b601f01601f19169290920160200192915050565b6020815260006115bd6020830184611a1b565b600060208284031215611a6c57600080fd5b5035919050565b80356001600160a01b0381168114611a8a57600080fd5b919050565b60008060408385031215611aa257600080fd5b611aab83611a73565b946020939093013593505050565b6000815160608452611ace6060850182611a1b565b905060208301518482036020860152611ae78282611a1b565b91505060408301518482036040860152611b018282611a1b565b95945050505050565b6020815260006115bd6020830184611ab9565b600080600060608486031215611b3257600080fd5b611b3b84611a73565b9250611b4960208501611a73565b9150604084013590509250925092565b634e487b7160e01b600052604160045260246000fd5b600067ffffffffffffffff80841115611b8a57611b8a611b59565b604051601f8501601f19908116603f01168101908282118183101715611bb257611bb2611b59565b81604052809350858152868686011115611bcb57600080fd5b858560208301376000602087830101525050509392505050565b600082601f830112611bf657600080fd5b6115bd83833560208501611b6f565b60008060008060808587031215611c1b57600080fd5b843567ffffffffffffffff80821115611c3357600080fd5b611c3f88838901611be5565b95506020870135915080821115611c5557600080fd5b611c6188838901611be5565b94506040870135915080821115611c7757600080fd5b50611c8487828801611be5565b925050611c9360608601611a73565b905092959194509250565b600060208284031215611cb057600080fd5b6115bd82611a73565b6000602080830181845280855180835260408601915060408160051b870101925083870160005b82811015611d0e57603f19888603018452611cfc858351611ab9565b94509285019290850190600101611ce0565b5092979650505050505050565b60008060408385031215611d2e57600080fd5b611d3783611a73565b915060208301358015158114611d4c57600080fd5b809150509250929050565b60008060008060808587031215611d6d57600080fd5b611d7685611a73565b9350611d8460208601611a73565b925060408501359150606085013567ffffffffffffffff811115611da757600080fd5b8501601f81018713611db857600080fd5b611dc787823560208401611b6f565b91505092959194509250565b60008060408385031215611de657600080fd5b611def83611a73565b9150611dfd60208401611a73565b90509250929050565b600181811c90821680611e1a57607f821691505b60208210811415611e3b57634e487b7160e01b600052602260045260246000fd5b50919050565b600080835481600182811c915080831680611e5d57607f831692505b6020808410821415611e7d57634e487b7160e01b86526022600452602486fd5b818015611e915760018114611ea257611ecf565b60ff19861689528489019650611ecf565b60008a81526020902060005b86811015611ec75781548b820152908501908301611eae565b505084890196505b509498975050505050505050565b60208082526031908201527f4552433732313a207472616e736665722063616c6c6572206973206e6f74206f6040820152701ddb995c881b9bdc88185c1c1c9bdd9959607a1b606082015260800190565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b634e487b7160e01b600052601160045260246000fd5b60008219821115611f8c57611f8c611f63565b500190565b634e487b7160e01b600052603260045260246000fd5b6000600019821415611fbb57611fbb611f63565b5060010190565b60008351611fd48184602088016119ef565b835190830190611fe88183602088016119ef565b01949350505050565b60008282101561200357612003611f63565b500390565b60208082526032908201527f4552433732313a207472616e7366657220746f206e6f6e20455243373231526560408201527131b2b4bb32b91034b6b83632b6b2b73a32b960711b606082015260800190565b6001600160a01b038581168252841660208201526040810183905260806060820181905260009061208d90830184611a1b565b9695505050505050565b6000602082840312156120a957600080fd5b81516115bd816119bc565b634e487b7160e01b600052601260045260246000fd5b6000826120d9576120d96120b4565b500490565b6000826120ed576120ed6120b4565b50069056fea2646970667358221220a4a3d16da34fa0d5748c509d4dd7daeff350f41f9a3bb31d0639b10a670139e064736f6c634300080c0033";

    public static final String FUNC_ADDRESSZERO = "addressZero";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_GETALLARTWORKS = "getAllArtworks";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_GETARTWORK = "getArtwork";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_safeTransferFrom = "safeTransferFrom";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOKENURI = "tokenURI";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    @Deprecated
    protected Contracts_Dragon721_sol_Dragon721(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Contracts_Dragon721_sol_Dragon721(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Contracts_Dragon721_sol_Dragon721(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Contracts_Dragon721_sol_Dragon721(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalForAllEventResponse>() {
            @Override
            public ApprovalForAllEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVALFORALL_EVENT, log);
                ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventFlowable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<String> addressZero() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADDRESSZERO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String to, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String owner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getAllArtworks() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETALLARTWORKS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Artwork>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<String> getApproved(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETAPPROVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Artwork> getArtwork(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETARTWORK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Artwork>() {}));
        return executeRemoteCallSingleValueReturn(function, Artwork.class);
    }

    public RemoteFunctionCall<Boolean> isApprovedForAll(String owner, String operator) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISAPPROVEDFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner), 
                new org.web3j.abi.datatypes.Address(160, operator)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> mint(String title, String artist, String metadataURI, String to) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(title), 
                new org.web3j.abi.datatypes.Utf8String(artist), 
                new org.web3j.abi.datatypes.Utf8String(metadataURI), 
                new org.web3j.abi.datatypes.Address(160, to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> ownerOf(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNEROF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_safeTransferFrom, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId, byte[] _data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_safeTransferFrom, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String operator, Boolean approved) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, operator), 
                new org.web3j.abi.datatypes.Bool(approved)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> tokenURI(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENURI, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Contracts_Dragon721_sol_Dragon721 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contracts_Dragon721_sol_Dragon721(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Contracts_Dragon721_sol_Dragon721 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contracts_Dragon721_sol_Dragon721(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Contracts_Dragon721_sol_Dragon721 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Contracts_Dragon721_sol_Dragon721(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Contracts_Dragon721_sol_Dragon721 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Contracts_Dragon721_sol_Dragon721(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Contracts_Dragon721_sol_Dragon721> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Contracts_Dragon721_sol_Dragon721.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Contracts_Dragon721_sol_Dragon721> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Contracts_Dragon721_sol_Dragon721.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Contracts_Dragon721_sol_Dragon721> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Contracts_Dragon721_sol_Dragon721.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Contracts_Dragon721_sol_Dragon721> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Contracts_Dragon721_sol_Dragon721.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class Artwork extends DynamicStruct {
        public String title;

        public String artist;

        public String metadataURI;

        public Artwork(String title, String artist, String metadataURI) {
            super(new org.web3j.abi.datatypes.Utf8String(title),new org.web3j.abi.datatypes.Utf8String(artist),new org.web3j.abi.datatypes.Utf8String(metadataURI));
            this.title = title;
            this.artist = artist;
            this.metadataURI = metadataURI;
        }

        public Artwork(Utf8String title, Utf8String artist, Utf8String metadataURI) {
            super(title,artist,metadataURI);
            this.title = title.getValue();
            this.artist = artist.getValue();
            this.metadataURI = metadataURI.getValue();
        }
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String approved;

        public BigInteger tokenId;
    }

    public static class ApprovalForAllEventResponse extends BaseEventResponse {
        public String owner;

        public String operator;

        public Boolean approved;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger tokenId;
    }
}
